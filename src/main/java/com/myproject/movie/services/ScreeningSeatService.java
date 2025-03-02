package com.myproject.movie.services;

import com.myproject.movie.models.dtos.commons.ScreeningSeatDto;
import java.util.List;

public interface ScreeningSeatService {
    public List<ScreeningSeatDto> getSeatsByScreeningId(Integer screeningId);
}
