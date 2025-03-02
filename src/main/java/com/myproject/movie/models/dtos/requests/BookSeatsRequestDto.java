package com.myproject.movie.models.dtos.requests;

import lombok.Data;

import java.util.List;

@Data
public class BookSeatsRequestDto {
    private List<Integer> seatIds;
}
