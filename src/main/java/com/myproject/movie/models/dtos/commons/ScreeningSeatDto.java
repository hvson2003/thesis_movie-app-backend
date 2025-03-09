package com.myproject.movie.models.dtos.commons;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ScreeningSeatDto {
    private Long id;

    @NotBlank(message = "Row is required")
    private String row;

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    @NotBlank(message = "Seat type is required")
    private String seatType;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Float price;
}
