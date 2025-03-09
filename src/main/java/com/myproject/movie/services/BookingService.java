package com.myproject.movie.services;

import com.myproject.movie.models.entities.Booking;

import java.util.List;

public interface BookingService {
    Booking bookSeats(Integer screeningId, List<Integer> seatIds, Integer userId);
}
