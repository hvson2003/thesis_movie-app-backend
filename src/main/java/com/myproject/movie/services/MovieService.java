package com.myproject.movie.services;

import com.myproject.movie.dtos.commons.MovieDTO;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<MovieDTO> getAllMovies();
    Optional<MovieDTO> getMovieById(Integer id);
    MovieDTO createMovie(MovieDTO movieDTO);
    MovieDTO updateMovie(Integer id, MovieDTO movieDTO);
    void deleteMovie(Integer id);
}
