package com.myproject.movie.models.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class BookSeatsRequestDto {
    @NotEmpty(message = "Seat IDs cannot be empty")
    private List<Long> seatIds;
}
