package com.myproject.movie.repositories;

import com.myproject.movie.models.entities.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    List<Theater> findByBrandIdAndCityIdAndRoomsScreeningsMovieId(Integer brandId, Integer cityId, Integer movieId);
}
