package com.myproject.movie.models.dtos.requests;

import com.myproject.movie.models.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {
    @NotNull(message = "Screening ID is required")
    private Integer screeningId;

    @NotNull(message = "Seat IDs are required")
    private List<Integer> seatIds;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
}
