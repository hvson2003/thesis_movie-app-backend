package com.myproject.movie.services;

import com.myproject.movie.models.entities.Theater;

import java.time.LocalDate;
import java.util.List;

public interface TheaterService {
    List<Theater> findAll();
    Theater findById(Long id);
    List<Theater> getTheatersByMovieCityAndDate(Long movieId, Long cityId, LocalDate date);
    List<Theater> getTheatersByMovieCityBrandAndDate(Long movieId, Long cityId, Long brandId, LocalDate date);
    Theater saveOrUpdate(Long id, Theater newTheater);
    void deleteById(Long id);
}
