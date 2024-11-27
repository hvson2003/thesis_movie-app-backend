package com.myproject.movie_booking.repositories;

import com.myproject.movie_booking.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findAll();
    Optional<Movie> findById(Integer id);
}
