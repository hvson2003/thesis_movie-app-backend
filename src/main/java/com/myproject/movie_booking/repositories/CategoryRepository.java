package com.myproject.movie_booking.repositories;

import com.myproject.movie_booking.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {}

