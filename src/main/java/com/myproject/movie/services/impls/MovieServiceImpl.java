package com.myproject.movie.services.impls;

import com.myproject.movie.dtos.entities.CategoryDTO;
import com.myproject.movie.dtos.entities.MovieDTO;
import com.myproject.movie.models.Category;
import com.myproject.movie.models.Movie;
import com.myproject.movie.repositories.CategoryRepository;
import com.myproject.movie.repositories.MovieRepository;
import com.myproject.movie.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getAllMoviesWithCategories() {
        return movieRepository.findAllMoviesWithCategories();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie createMovie(MovieDTO movieDTO) {
        List<Category> categories = categoryRepository.findAllById(
                movieDTO.getCategories().stream()
                        .map(CategoryDTO::getCategoryId)
                        .collect(Collectors.toList())
        );

        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setDuration(movieDTO.getDuration());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setDirector(movieDTO.getDirector());
        movie.setActors(movieDTO.getActors());
        movie.setLanguage(movieDTO.getLanguage());
        movie.setSubtitle(movieDTO.getSubtitle());
        movie.setRating(movieDTO.getRating());
        movie.setPosterUrl(movieDTO.getPosterUrl());
        movie.setTrailerUrl(movieDTO.getTrailerUrl());
        movie.setCategories(categories);

        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, MovieDTO movieDTO) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie != null) {
            movie.setTitle(movieDTO.getTitle());
            movie.setDescription(movieDTO.getDescription());
            movie.setDuration(movieDTO.getDuration());
            movie.setReleaseDate(movieDTO.getReleaseDate());
            movie.setDirector(movieDTO.getDirector());
            movie.setActors(movieDTO.getActors());
            movie.setLanguage(movieDTO.getLanguage());
            movie.setSubtitle(movieDTO.getSubtitle());
            movie.setRating(movieDTO.getRating());
            movie.setPosterUrl(movieDTO.getPosterUrl());
            movie.setTrailerUrl(movieDTO.getTrailerUrl());

            List<Category> categories = categoryRepository.findAllById(
                    movieDTO.getCategories().stream()
                            .map(CategoryDTO::getCategoryId)
                            .collect(Collectors.toList())
            );
            movie.setCategories(categories);

            return movieRepository.save(movie);
        }
        return null;
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}