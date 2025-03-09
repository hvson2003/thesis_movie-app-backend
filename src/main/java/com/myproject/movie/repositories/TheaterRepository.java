package com.myproject.movie.repositories;

import com.myproject.movie.models.entities.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
    List<Theater> findByBrandIdAndCityIdAndRoomsScreeningsMovieIdAndRoomsScreeningsStartTimeBetween(
            Long brandId, Long cityId, Long movieId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
