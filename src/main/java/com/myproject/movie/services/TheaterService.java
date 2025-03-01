package com.myproject.movie.services;

import com.myproject.movie.models.entities.Theater;

import java.time.LocalDate;
import java.util.List;

public interface TheaterService {
    List<Theater> findAll();
    Theater findById(Integer id);
    List<Theater> getTheatersByMovieCityBrandAndDate(Integer id, Integer cityId, Integer brandId, LocalDate date);
    Theater saveOrUpdate(Integer id, Theater newTheater);
    void deleteById(Integer id);
}
