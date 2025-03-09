package com.myproject.movie.models.dtos.responses;

import com.myproject.movie.models.enums.PaymentMethod;
import com.myproject.movie.models.enums.PaymentStatus;
import lombok.Data;

@Data
public class PaymentResponseDto {
    private Integer id;
    private Integer bookingId;
    private Float amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String transactionId;
    private String paymentGateway;
    private String qrCodeUrl;
    private String clientSecret;
}
