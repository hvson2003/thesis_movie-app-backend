package com.myproject.movie.services;

import com.myproject.movie.models.dtos.commons.ScreeningSeatDto;
import java.util.List;

public interface ScreeningService {
    List<ScreeningSeatDto> getSeatsByScreeningId(Long screeningId);
    void bookSeats(Long screeningId, List<Long> seatIds);
}
