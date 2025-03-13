package com.myproject.movie.repositories;

import com.myproject.movie.models.entities.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    @Query("SELECT t FROM Theater t " +
            "JOIN t.rooms r JOIN r.screenings s " +
            "WHERE t.city.id = :cityId AND s.movie.id = :movieId " +
            "AND s.startTime BETWEEN :startTime AND :endTime " +
            "ORDER BY t.brand.id")
    List<Theater> findByCityIdAndRoomsScreeningsMovieIdAndRoomsScreeningsStartTimeBetween(
            Long cityId, Long movieId, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT t FROM Theater t " +
            "JOIN t.rooms r JOIN r.screenings s " +
            "WHERE t.brand.id = :brandId AND t.city.id = :cityId AND s.movie.id = :movieId " +
            "AND s.startTime BETWEEN :startTime AND :endTime " +
            "ORDER BY t.brand.id")
    List<Theater> findByBrandIdAndCityIdAndRoomsScreeningsMovieIdAndRoomsScreeningsStartTimeBetween(
            Long brandId, Long cityId, Long movieId, LocalDateTime startTime, LocalDateTime endTime);
}
