package com.myproject.movie.models.dtos.commons;

import lombok.Data;

@Data
public class ScreeningSeatDto {
    private Integer id;
    private String row;
    private String seatNumber;
    private String seatType;
    private String status;
    private Float price;
}
