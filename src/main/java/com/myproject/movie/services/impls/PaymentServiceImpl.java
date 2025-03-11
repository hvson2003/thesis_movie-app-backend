package com.myproject.movie.services.impls;

import com.myproject.movie.mappers.PaymentMapper;
import com.myproject.movie.models.dtos.responses.PaymentResponseDto;
import com.myproject.movie.models.entities.Booking;
import com.myproject.movie.models.entities.BookingSeat;
import com.myproject.movie.models.entities.Payment;
import com.myproject.movie.models.entities.ScreeningSeat;
import com.myproject.movie.models.enums.BookingStatus;
import com.myproject.movie.models.enums.PaymentMethod;
import com.myproject.movie.models.enums.PaymentStatus;
import com.myproject.movie.models.enums.SeatStatus;
import com.myproject.movie.repositories.BookingRepository;
import com.myproject.movie.repositories.BookingSeatRepository;
import com.myproject.movie.repositories.PaymentRepository;
import com.myproject.movie.repositories.ScreeningSeatRepository;
import com.myproject.movie.services.PaymentService;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final ScreeningSeatRepository screeningSeatRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final PaymentMapper paymentMapper;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Override
    @Transactional
    public PaymentResponseDto initiatePayment(Long bookingId, PaymentMethod paymentMethod) {
        log.info("Initiating payment for bookingId: {}, method: {}", bookingId, paymentMethod);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingId));

        Payment payment = createPayment(booking, paymentMethod);
        processPayment(payment, paymentMethod, booking);

        if (payment.getStatus() == PaymentStatus.SUCCESSFUL) {
            confirmBooking(booking);
        }

        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toPaymentResponseDto(savedPayment);
    }

    private Payment createPayment(Booking booking, PaymentMethod paymentMethod) {
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setUser(booking.getUser());
        payment.setAmount(booking.getTotalAmount());
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(PaymentStatus.PENDING);
        return payment;
    }

    private void confirmBooking(Booking booking) {
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        List<BookingSeat> bookingSeats = bookingSeatRepository.findByBookingId(booking.getId());
        if (bookingSeats.isEmpty()) {
            log.warn("No seats found for bookingId: {}", booking.getId());
            return;
        }

        List<Long> screeningSeatIds = bookingSeats.stream()
                .map(bs -> bs.getScreeningSeat().getId())
                .collect(Collectors.toList());

        List<ScreeningSeat> seatsToConfirm = screeningSeatRepository.findByIdIn(screeningSeatIds);
        if (seatsToConfirm.isEmpty()) {
            log.error("No ScreeningSeats found for screeningSeatIds: {}", screeningSeatIds);
            throw new IllegalStateException("No seats found to confirm for bookingId: " + booking.getId());
        }

        seatsToConfirm.forEach(seat -> {
            if (seat.getStatus() == SeatStatus.RESERVED) {
                seat.setStatus(SeatStatus.BOOKED);
            }
        });
        screeningSeatRepository.saveAll(seatsToConfirm);
        log.info("Confirmed bookingId: {}, updated {} seats to BOOKED", booking.getId(), seatsToConfirm.size());
    }

    private void processPayment(Payment payment, PaymentMethod paymentMethod, Booking booking) {
        if (paymentMethod == PaymentMethod.CREDIT_CARD) {
            processStripePayment(payment, booking);
        } else if (paymentMethod == PaymentMethod.BANK_TRANSFER) {
            processBankTransferPayment(payment, booking);
        }
    }

    private void processStripePayment(Payment payment, Booking booking) {
        Stripe.apiKey = stripeApiKey;
        payment.setPaymentGateway("STRIPE");

        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(booking.getTotalAmount().longValue())
                    .setCurrency("vnd")
                    .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.AUTOMATIC)
                    .setPaymentMethod("pm_card_visa")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            payment.setTransactionId(intent.getId());
            payment.setClientSecret(intent.getClientSecret());

            payment.setStatus(PaymentStatus.SUCCESSFUL); // Should be replaced with actual verification from Stripe
        } catch (Exception e) {
            payment.setStatus(PaymentStatus.FAILED);
            throw new RuntimeException("Stripe payment initiation failed: " + e.getMessage(), e);
        }
    }

    private void processBankTransferPayment(Payment payment, Booking booking) {
        payment.setPaymentGateway("VIETQR");
        String qrCodeUrl = generateVietQRCode(booking.getTotalAmount(), booking.getId());
        payment.setQrCodeUrl(qrCodeUrl);
    }

    private String generateVietQRCode(Float amount, Long bookingId) {
        return "https://vietqr.example.com/qr?amount=" + amount + "&bookingId=" + bookingId;
    }
}
