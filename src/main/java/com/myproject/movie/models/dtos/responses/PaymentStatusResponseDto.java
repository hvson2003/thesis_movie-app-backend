package com.myproject.movie.models.dtos.responses;

import com.myproject.movie.models.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentStatusResponseDto {
    private String status;
}
