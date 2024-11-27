package com.myproject.movie_booking.services;

import com.myproject.movie_booking.models.Movie;
import com.myproject.movie_booking.models.Screening;
import com.myproject.movie_booking.models.ScreeningSeat;
import com.myproject.movie_booking.repositories.MovieRepository;
import com.myproject.movie_booking.repositories.ScreeningRepository;
import com.myproject.movie_booking.repositories.ScreeningSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private ScreeningSeatRepository screeningSeatRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Integer id) {
        return movieRepository.findById(id);
    }

    public List<ScreeningSeat> getAvailableSeatsByMovieId(Integer movieId) {
        List<Screening> screenings = screeningRepository.findByMovieId(movieId);
        List<Integer> screeningIds = screenings.stream()
                .map(Screening::getId)
                .collect(Collectors.toList());
        return screeningSeatRepository.findByScreeningIdInAndStatus(screeningIds, "available");
    }
}
