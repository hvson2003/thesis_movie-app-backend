package com.myproject.movie_booking.controllers;

import com.myproject.movie_booking.models.Movie;
import com.myproject.movie_booking.models.ScreeningSeat;
import com.myproject.movie_booking.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Integer id) {
        Optional<Movie> movie = movieService.getMovieById(id);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/available-seats/{movieId}")
    public List<ScreeningSeat> getAvailableSeatsByMovieId(@PathVariable Integer movieId) {
        return movieService.getAvailableSeatsByMovieId(movieId);
    }
}
