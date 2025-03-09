package com.myproject.movie.services.impls;

import com.myproject.movie.mappers.MovieMapper;
import com.myproject.movie.models.dtos.commons.CategoryDto;
import com.myproject.movie.models.dtos.commons.MovieDto;
import com.myproject.movie.models.entities.Category;
import com.myproject.movie.models.entities.Movie;
import com.myproject.movie.repositories.CategoryRepository;
import com.myproject.movie.repositories.MovieRepository;
import com.myproject.movie.services.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final MovieMapper movieMapper;

    @Override
    public List<MovieDto> getAllMovies() {
        log.info("Fetching all movies");
        List<Movie> movies = movieRepository.findAll();
        log.debug("Retrieved {} movies", movies.size());
        return movies.stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MovieDto> getMovieById(Long id) {
        log.info("Fetching movie with id: {}", id);
        return movieRepository.findById(id)
                .map(movieMapper::toDTO);
    }

    @Override
    @Transactional
    public MovieDto createMovie(MovieDto movieDto) {
        log.info("Creating new movie: {}", movieDto.getTitle());
        Movie movie = movieMapper.toEntity(movieDto);
        setCategories(movie, movieDto.getCategories());
        Movie savedMovie = movieRepository.save(movie);
        return movieMapper.toDTO(savedMovie);
    }

    @Override
    @Transactional
    public MovieDto updateMovie(Long id, MovieDto movieDto) {
        log.info("Updating movie with id: {}", id);
        return movieRepository.findById(id)
                .map(existingMovie -> {
                    Movie updatedMovie = movieMapper.toEntity(movieDto);
                    updatedMovie.setId(id);
                    setCategories(updatedMovie, movieDto.getCategories());
                    return movieMapper.toDTO(movieRepository.save(updatedMovie));
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {
        log.info("Deleting movie with id: {}", id);
        if (!movieRepository.existsById(id)) {
            throw new IllegalArgumentException("Movie not found with id: " + id);
        }
        movieRepository.deleteById(id);
    }

    private void setCategories(Movie movie, List<CategoryDto> categoryDtos) {
        if (categoryDtos != null && !categoryDtos.isEmpty()) {
            List<Long> categoryIds = categoryDtos.stream()
                    .map(CategoryDto::getId)
                    .collect(Collectors.toList());
            List<Category> categories = categoryRepository.findAllById(categoryIds);
            if (categories.size() != categoryIds.size()) {
                throw new IllegalArgumentException("One or more category IDs are invalid");
            }
            movie.setCategories(categories);
        }
    }
}
