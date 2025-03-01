package com.myproject.movie.repositories;

import com.myproject.movie.models.entities.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    List<Theater> findByBrandIdAndCityIdAndRoomsScreeningsMovieIdAndRoomsScreeningsStartTimeBetween(
            Integer brandId, Integer cityId, Integer movieId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
