package com.myproject.movie.services;

import com.myproject.movie.models.dtos.responses.PaymentResponseDto;
import com.myproject.movie.models.enums.PaymentMethod;

public interface PaymentService {
    PaymentResponseDto initiatePayment(Integer bookingId, PaymentMethod paymentMethod);
}
