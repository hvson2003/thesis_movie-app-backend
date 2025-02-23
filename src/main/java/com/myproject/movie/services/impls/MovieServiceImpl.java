package com.myproject.movie.services.impls;

import com.myproject.movie.dtos.commons.CategoryDTO;
import com.myproject.movie.dtos.commons.MovieDTO;
import com.myproject.movie.mappers.MovieMapper;
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
    private final MovieMapper movieMapper;

    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MovieDTO> getMovieById(Integer id) {
        return movieRepository.findById(id)
                .map(movieMapper::toDTO);
    }

    public MovieDTO createMovie(MovieDTO movieDTO) {
        Movie movie = movieMapper.toEntity(movieDTO);
        List<Category> categories = categoryRepository.findAllById(
                movieDTO.getCategories().stream()
                        .map(CategoryDTO::getId)
                        .collect(Collectors.toList())
        );
        movie.setCategories(categories);

        return movieMapper.toDTO(movieRepository.save(movie));
    }

    public MovieDTO updateMovie(Integer id, MovieDTO movieDTO) {
        return movieRepository.findById(id).map(existingMovie -> {
            Movie updatedMovie = movieMapper.toEntity(movieDTO);
            updatedMovie.setId(id);

            List<Category> categories = categoryRepository.findAllById(
                    movieDTO.getCategories().stream()
                            .map(CategoryDTO::getId)
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