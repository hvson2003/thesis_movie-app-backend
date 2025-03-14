package com.myproject.movie.models.dtos.requests;

import lombok.Data;

@Data
public class InitiatePaymentRequestDto {
    private Long bookingId;
    private String paymentMethod;
}
