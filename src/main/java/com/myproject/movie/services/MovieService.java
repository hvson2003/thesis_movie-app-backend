package com.myproject.movie.services;

import com.myproject.movie.dtos.entities.MovieDTO;
import com.myproject.movie.models.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> getAllMovies();
    Optional<Movie> getMovieById(Long id);
    Movie createMovie(MovieDTO movieDTO);
    Movie updateMovie(Long id, MovieDTO movieDTO);
    void deleteMovie(Long id);
}
