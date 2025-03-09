package com.myproject.movie.controllers;

import com.myproject.movie.models.dtos.requests.BookingRequest;
import com.myproject.movie.models.dtos.responses.PaymentResponseDto;
import com.myproject.movie.models.entities.Booking;
import com.myproject.movie.services.BookingService;
import com.myproject.movie.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final BookingService bookingService;
    private final PaymentService paymentService;

    @PostMapping("/book-and-pay")
    public ResponseEntity<PaymentResponseDto> bookAndPay(@Valid @RequestBody BookingRequest request) {
        try {
            Booking booking = bookingService.bookSeats(request.getScreeningId(), request.getSeatIds(), request.getUserId());
            PaymentResponseDto payment = paymentService.initiatePayment(booking.getId(), request.getPaymentMethod());

            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new PaymentResponseDto());
        }
    }
}