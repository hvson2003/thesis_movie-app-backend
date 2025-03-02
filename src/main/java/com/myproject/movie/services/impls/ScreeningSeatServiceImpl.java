package com.myproject.movie.services.impls;

import com.myproject.movie.mappers.SeatScreeningMapper;
import com.myproject.movie.models.dtos.commons.ScreeningSeatDto;
import com.myproject.movie.models.entities.ScreeningSeat;
import com.myproject.movie.repositories.ScreeningSeatRepository;
import com.myproject.movie.services.ScreeningSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScreeningSeatServiceImpl implements ScreeningSeatService {
    private final ScreeningSeatRepository screeningSeatRepository;
    private final SeatScreeningMapper seatScreeningMapper;

    public List<ScreeningSeatDto> getSeatsByScreeningId(Integer screeningId) {
        List<ScreeningSeat> seats = screeningSeatRepository.findByScreeningId(screeningId);
        return seats.stream().map(seatScreeningMapper::seatToSeatDto)
                .sorted(Comparator
                    .comparing(ScreeningSeatDto::getRow)
                        .thenComparing(dto -> Integer.parseInt(dto.getSeatNumber())))
                .collect(Collectors.toList());
    }
}