package com.myproject.movie.services;

import com.myproject.movie.models.dtos.commons.MovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<MovieDto> getAllMovies();
    Optional<MovieDto> getMovieById(Long id);
    MovieDto createMovie(MovieDto movieDTO);
    MovieDto updateMovie(Long id, MovieDto movieDTO);
    void deleteMovie(Long id);
}
