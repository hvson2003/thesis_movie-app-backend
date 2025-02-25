package com.myproject.movie.services.impls;

import com.myproject.movie.models.dtos.commons.CategoryDto;
import com.myproject.movie.models.dtos.commons.MovieDto;
import com.myproject.movie.mappers.MovieMapper;
import com.myproject.movie.models.entities.Category;
import com.myproject.movie.models.entities.Movie;
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
    private final MovieMapper movieMapper;

    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MovieDto> getMovieById(Integer id) {
        return movieRepository.findById(id)
                .map(movieMapper::toDTO);
    }

    public MovieDto createMovie(MovieDto movieDTO) {
        Movie movie = movieMapper.toEntity(movieDTO);
        List<Category> categories = categoryRepository.findAllById(
                movieDTO.getCategories().stream()
                        .map(CategoryDto::getId)
                        .collect(Collectors.toList())
        );
        movie.setCategories(categories);

        return movieMapper.toDTO(movieRepository.save(movie));
    }

    public MovieDto updateMovie(Integer id, MovieDto movieDTO) {
        return movieRepository.findById(id).map(existingMovie -> {
            Movie updatedMovie = movieMapper.toEntity(movieDTO);
            updatedMovie.setId(id);

            List<Category> categories = categoryRepository.findAllById(
                    movieDTO.getCategories().stream()
                            .map(CategoryDto::getId)
                            .collect(Collectors.toList())
            );
            updatedMovie.setCategories(categories);

            return movieMapper.toDTO(movieRepository.save(updatedMovie));
        }).orElse(null);
    }

    public void deleteMovie(Integer id) {
        movieRepository.deleteById(id);
    }
}