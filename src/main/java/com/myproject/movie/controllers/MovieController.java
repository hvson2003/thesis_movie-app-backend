package com.myproject.movie.controllers;

import com.myproject.movie.models.dtos.commons.MovieDto;
import com.myproject.movie.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public List<MovieDto> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Integer id) {
        Optional<MovieDto> movieDTO = movieService.getMovieById(id);
        return movieDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public MovieDto createMovie(@RequestBody MovieDto movieDTO) {
        return movieService.createMovie(movieDTO);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public MovieDto updateMovie(@PathVariable Integer id, @RequestBody MovieDto movieDTO) {
        return movieService.updateMovie(id, movieDTO);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
    }
}
