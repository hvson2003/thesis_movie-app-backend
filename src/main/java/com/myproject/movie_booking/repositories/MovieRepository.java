package com.myproject.movie_booking.repositories;

import com.myproject.movie_booking.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query("SELECT m FROM Movie m JOIN FETCH m.categories mc JOIN FETCH mc.category")
    List<Movie> findAllMoviesWithCategories();
}
