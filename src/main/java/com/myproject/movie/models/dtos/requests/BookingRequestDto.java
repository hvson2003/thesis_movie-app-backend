package com.myproject.movie.models.dtos.requests;

import com.myproject.movie.models.enums.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BookingRequestDto {
    @NotNull(message = "Screening ID is required")
    private Long screeningId;

    @NotEmpty(message = "Screening seat IDs cannot be empty")
    private List<Long> screeningSeatIds;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
}
