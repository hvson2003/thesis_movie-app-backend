package com.myproject.movie.repositories;

import com.myproject.movie.models.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m JOIN FETCH m.categories")
    List<Movie> findAllMoviesWithCategories();
}
