package com.myproject.movie.services;

import com.myproject.movie.models.entities.Theater;
import java.util.List;

public interface TheaterService {
    List<Theater> findAll();
    Theater findById(Integer id);
    Theater saveOrUpdate(Integer id, Theater newTheater);
    Theater updatePartialTheaterById(Integer id, Theater updatedData);
    void deleteById(Integer id);
}
