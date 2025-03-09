package com.myproject.movie.repositories;

import com.myproject.movie.models.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {}

