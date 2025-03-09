package com.myproject.movie.services.impls;

import com.myproject.movie.models.entities.Theater;
import com.myproject.movie.repositories.TheaterRepository;
import com.myproject.movie.services.TheaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TheaterServiceImpl implements TheaterService {
    private final TheaterRepository theaterRepository;

    @Override
    public List<Theater> findAll() {
        log.info("Fetching all theaters");
        List<Theater> theaters = theaterRepository.findAll();
        log.debug("Retrieved {} theaters", theaters.size());

        return theaters;
    }

    @Override
    public Theater findById(Long id) {
        log.info("Fetching theater with id: {}", id);

        return theaterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Theater not found with id: " + id));
    }

    @Override
    public List<Theater> getTheatersByMovieCityBrandAndDate(Long movieId, Long cityId, Long brandId, LocalDate date) {
        log.info("Fetching theaters for movieId: {}, cityId: {}, brandId: {}, date: {}", movieId, cityId, brandId, date);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<Theater> theaters = theaterRepository.findByBrandIdAndCityIdAndRoomsScreeningsMovieIdAndRoomsScreeningsStartTimeBetween(
                brandId, cityId, movieId, startOfDay, endOfDay);

        theaters.forEach(theater -> theater.getRooms().forEach(room ->
                room.setScreenings(room.getScreenings().stream()
                        .filter(screening -> !screening.getStartTime().isBefore(startOfDay) &&
                                !screening.getStartTime().isAfter(endOfDay))
                        .toList())
        ));

        log.debug("Retrieved {} theaters", theaters.size());
        return theaters;
    }

    @Override
    @Transactional
    public Theater saveOrUpdate(Long id, Theater theater) {
        log.info("Saving or updating theater with id: {}", id);
        if (id != null) {
            if (!theaterRepository.existsById(id)) {
                throw new IllegalArgumentException("Theater not found with id: " + id);
            }
            theater.setId(id); // Đảm bảo ID được gán đúng khi cập nhật
        }

        return theaterRepository.save(theater);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Deleting theater with id: {}", id);
        if (!theaterRepository.existsById(id)) {
            throw new IllegalArgumentException("Theater not found with id: " + id);
        }
        theaterRepository.deleteById(id);
    }
}
