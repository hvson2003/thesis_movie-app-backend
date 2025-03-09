package com.myproject.movie.services.impls;

import com.myproject.movie.mappers.SeatScreeningMapper;
import com.myproject.movie.models.dtos.commons.ScreeningSeatDto;
import com.myproject.movie.models.entities.ScreeningSeat;
import com.myproject.movie.models.enums.SeatStatus;
import com.myproject.movie.repositories.ScreeningSeatRepository;
import com.myproject.movie.services.ScreeningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScreeningServiceImpl implements ScreeningService {
    private final ScreeningSeatRepository screeningSeatRepository;
    private final SeatScreeningMapper seatScreeningMapper;

    @Override
    public List<ScreeningSeatDto> getSeatsByScreeningId(Long screeningId) {
        log.info("Fetching seats for screeningId: {}", screeningId);
        List<ScreeningSeat> seats = screeningSeatRepository.findByScreeningId(screeningId);
        List<ScreeningSeatDto> seatDtos = seats.stream()
                .map(seatScreeningMapper::seatToSeatDto)
                .sorted(Comparator
                        .comparing(ScreeningSeatDto::getRow)
                        .thenComparing(ScreeningSeatDto::getSeatNumber, Comparator.comparing(Long::valueOf)))
                .collect(Collectors.toList());
        log.debug("Retrieved {} seats for screeningId: {}", seatDtos.size(), screeningId);

        return seatDtos;
    }

    @Override
    @Transactional
    public void bookSeats(Long screeningId, List<Long> seatIds) {
        log.info("Booking seats for screeningId: {}, seatIds: {}", screeningId, seatIds);
        if (seatIds == null || seatIds.isEmpty()) {
            throw new IllegalArgumentException("Danh sách ghế không được rỗng");
        }

        List<ScreeningSeat> seats = screeningSeatRepository.findByScreeningId(screeningId);
        validateSeats(seats, seatIds);

        seats.forEach(seat -> {
            seat.setStatus(SeatStatus.RESERVED);
            screeningSeatRepository.save(seat);
        });
        log.info("Successfully booked {} seats for screeningId: {}", seats.size(), screeningId);
    }

    private void validateSeats(List<ScreeningSeat> seats, List<Long> seatIds) {
        if (seats.size() != seatIds.size()) {
            throw new IllegalArgumentException("Một hoặc nhiều ghế không tồn tại trong suất chiếu này");
        }

        Set<Long> unavailableSeatIds = seats.stream()
                .filter(seat -> seat.getStatus() != SeatStatus.AVAILABLE)
                .map(ScreeningSeat::getId)
                .collect(Collectors.toSet());

        if (!unavailableSeatIds.isEmpty()) {
            throw new IllegalArgumentException("Các ghế không khả dụng: " + unavailableSeatIds);
        }
    }
}
