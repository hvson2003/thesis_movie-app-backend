package com.myproject.movie.repositories;

import com.myproject.movie.models.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    List<Brand> findByCityId(Integer cityId);
}