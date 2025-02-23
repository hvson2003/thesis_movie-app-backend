package com.myproject.movie.repositories;

import com.myproject.movie.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {}

