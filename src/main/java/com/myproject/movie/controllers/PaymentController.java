package com.myproject.movie.controllers;

import com.myproject.movie.models.dtos.requests.BookingRequestDto;
import com.myproject.movie.models.dtos.requests.InitiatePaymentRequestDto;
import com.myproject.movie.models.dtos.responses.PaymentResponseDto;
import com.myproject.movie.models.dtos.responses.PaymentStatusResponseDto;
import com.myproject.movie.models.entities.Booking;
import com.myproject.movie.models.enums.PaymentMethod;
import com.myproject.movie.services.BookingService;
import com.myproject.movie.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final BookingService bookingService;
    private final PaymentService paymentService;

    @PostMapping("/book-and-pay")
    public ResponseEntity<PaymentResponseDto> bookAndPay(@Valid @RequestBody BookingRequestDto request) {
        Booking booking = bookingService.bookSeats(
                request.getScreeningId(),
                request.getScreeningSeatIds(),
                request.getUserId()
        );
        PaymentResponseDto paymentResponse = paymentService.initiatePayment(
                booking.getId(),
                request.getPaymentMethod()
        );
        return ResponseEntity.ok(paymentResponse);
    }

    @PostMapping("/initiate")
    public ResponseEntity<PaymentResponseDto> initiatePayment(@RequestBody InitiatePaymentRequestDto request) {
        PaymentResponseDto paymentResponse = paymentService.initiatePayment(
                request.getBookingId(),
                PaymentMethod.valueOf(request.getPaymentMethod())
        );
        return ResponseEntity.ok(paymentResponse);
    }

    @PostMapping("/confirm/{paymentIntentId}")
    public ResponseEntity<PaymentResponseDto> confirmPayment(@PathVariable String paymentIntentId) {
        PaymentResponseDto response = paymentService.confirmPayment(paymentIntentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{transactionId}")
    public ResponseEntity<PaymentStatusResponseDto> checkPaymentStatus(@PathVariable String transactionId) {
        PaymentResponseDto paymentResponse = paymentService.getPaymentStatus(transactionId);
        return ResponseEntity.ok(new PaymentStatusResponseDto(paymentResponse.getStatus().toString()));
    }
}
