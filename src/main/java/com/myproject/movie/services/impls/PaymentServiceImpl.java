package com.myproject.movie.services.impls;

import com.myproject.movie.mappers.PaymentMapper;
import com.myproject.movie.models.dtos.responses.PaymentResponseDto;
import com.myproject.movie.models.entities.Booking;
import com.myproject.movie.models.entities.Payment;
import com.myproject.movie.models.enums.PaymentMethod;
import com.myproject.movie.models.enums.PaymentStatus;
import com.myproject.movie.repositories.BookingRepository;
import com.myproject.movie.repositories.PaymentRepository;
import com.myproject.movie.services.PaymentService;
import com.myproject.movie.services.ScreeningSeatService;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Override
    public PaymentResponseDto initiatePayment(Integer bookingId, PaymentMethod paymentMethod) {
        Stripe.apiKey = stripeApiKey;

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setUser(booking.getUser());
        payment.setAmount(booking.getTotalAmount());
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(PaymentStatus.PENDING);

        if (paymentMethod == PaymentMethod.CREDIT_CARD) {
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
                payment.setStatus(PaymentStatus.PENDING);
                payment.setClientSecret(intent.getClientSecret());
            } catch (Exception e) {
                payment.setStatus(PaymentStatus.FAILED);
                throw new RuntimeException("Stripe payment failed: " + e.getMessage());
            }
        } else if (paymentMethod == PaymentMethod.BANK_TRANSFER) {
            payment.setPaymentGateway("VIETQR");
            String qrCodeUrl = generateVietQRCode(booking.getTotalAmount(), bookingId);
            payment.setQrCodeUrl(qrCodeUrl);
        }

        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toPaymentResponseDto(savedPayment);
    }

    private String generateVietQRCode(Float amount, Integer bookingId) {
        return "https://vietqr.example.com/qr?amount=" + amount + "&bookingId=" + bookingId;
    }
}
