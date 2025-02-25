package com.myproject.movie.services;

import com.myproject.movie.models.dtos.commons.MovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<MovieDto> getAllMovies();
    Optional<MovieDto> getMovieById(Integer id);
    MovieDto createMovie(MovieDto movieDTO);
    MovieDto updateMovie(Integer id, MovieDto movieDTO);
    void deleteMovie(Integer id);
}
