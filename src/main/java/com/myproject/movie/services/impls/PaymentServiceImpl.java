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
import com.stripe.exception.StripeException;
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

    @Override
    public PaymentResponseDto getPaymentStatus(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found with transactionId: " + transactionId));
        return paymentMapper.toPaymentResponseDto(payment);
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
        validateBookingSeats(booking, bookingSeats);

        List<Long> screeningSeatIds = bookingSeats.stream()
                .map(bs -> bs.getScreeningSeat().getId())
                .collect(Collectors.toList());

        List<ScreeningSeat> seatsToConfirm = fetchScreeningSeats(screeningSeatIds, booking.getId());
        updateSeatStatus(seatsToConfirm, booking.getId());
    }

    private void validateBookingSeats(Booking booking, List<BookingSeat> bookingSeats) {
        if (bookingSeats.isEmpty()) {
            log.warn("No seats found for bookingId: {}", booking.getId());
            throw new IllegalStateException("No seats associated with bookingId: " + booking.getId());
        }
    }

    private List<ScreeningSeat> fetchScreeningSeats(List<Long> screeningSeatIds, Long bookingId) {
        List<ScreeningSeat> seats = screeningSeatRepository.findByIdIn(screeningSeatIds);
        if (seats.isEmpty()) {
            log.error("No ScreeningSeats found for screeningSeatIds: {}", screeningSeatIds);
            throw new IllegalStateException("No seats found to confirm for bookingId: " + bookingId);
        }
        return seats;
    }

    private void updateSeatStatus(List<ScreeningSeat> seats, Long bookingId) {
        seats.forEach(seat -> {
            if (seat.getStatus() == SeatStatus.RESERVED) {
                seat.setStatus(SeatStatus.BOOKED);
            }
        });
        screeningSeatRepository.saveAll(seats);
        log.info("Confirmed bookingId: {}, updated {} seats to BOOKED", bookingId, seats.size());
    }

    private void processPayment(Payment payment, PaymentMethod paymentMethod, Booking booking) {
        switch (paymentMethod) {
            case CREDIT_CARD:
                processStripePayment(payment, booking);
                break;
            case BANK_TRANSFER:
                processVietQRPayment(payment, booking);
                break;
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
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
            payment.setStatus(PaymentStatus.PENDING);

            paymentRepository.save(payment);
        } catch (Exception e) {
            payment.setStatus(PaymentStatus.FAILED);
            log.error("Stripe payment initiation failed for bookingId: {}", booking.getId(), e);
            throw new RuntimeException("Stripe payment initiation failed: " + e.getMessage(), e);
        }
    }

    private void processVietQRPayment(Payment payment, Booking booking) {
        payment.setPaymentGateway("VIETQR");

        try {
            String qrCodeUrl = generateVietQRCode(booking.getTotalAmount(), booking.getId());
            payment.setQrCodeUrl(qrCodeUrl);
            payment.setStatus(PaymentStatus.PENDING);
            payment.setTransactionId("VIETQR-" + booking.getId());
        } catch (Exception e) {
            payment.setStatus(PaymentStatus.FAILED);
            log.error("VietQR payment initiation failed for bookingId: {}", booking.getId(), e);
            throw new RuntimeException("VietQR payment initiation failed: " + e.getMessage(), e);
        }
    }

    @Transactional
    public PaymentResponseDto confirmPayment(String paymentIntentId) {
        try {
            Stripe.apiKey = stripeApiKey;
            PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);

            Payment payment = paymentRepository.findByTransactionId(paymentIntentId)
                    .orElseThrow(() -> new IllegalArgumentException("Payment not found for intent: " + paymentIntentId));

            if ("succeeded".equals(intent.getStatus())) {
                payment.setStatus(PaymentStatus.SUCCESSFUL);
                confirmBooking(payment.getBooking());
            } else if ("requires_payment_method".equals(intent.getStatus()) ||
                    "requires_action".equals(intent.getStatus())) {
                payment.setStatus(PaymentStatus.PENDING);
            } else {
                payment.setStatus(PaymentStatus.FAILED);
            }

            Payment updatedPayment = paymentRepository.save(payment);
            return paymentMapper.toPaymentResponseDto(updatedPayment);

        } catch (StripeException e) {
            log.error("Error confirming payment intent: {}", paymentIntentId, e);
            throw new RuntimeException("Payment confirmation failed: " + e.getMessage(), e);
        }
    }

    private String generateVietQRCode(Float amount, Long bookingId) {
        String bankId = "970415";
        String accountNo = "106875083952";
        String template = "compact";
        String accountName = "Movie Booking";
        String description = "Thanh toan dat ve " + bookingId;

        return String.format(
                "https://img.vietqr.io/image/%s-%s-%s.png?amount=%.0f&addInfo=%s&accountName=%s",
                bankId, accountNo, template, amount, description, accountName
        );
    }
}
