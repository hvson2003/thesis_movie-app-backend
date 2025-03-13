package com.myproject.movie.controllers;

import com.myproject.movie.models.entities.Theater;
import com.myproject.movie.services.TheaterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/theaters")
@RequiredArgsConstructor
public class TheaterController {
    private final TheaterService theaterService;

    @GetMapping
    public ResponseEntity<List<Theater>> getAllTheaters() {
        List<Theater> theaters = theaterService.findAll();

        return ResponseEntity.ok(theaters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theater> getTheaterById(@PathVariable Long id) {
        Theater theater = theaterService.findById(id);

        return ResponseEntity.ok(theater);
    }

    @GetMapping("/movie/{movieId}/city/{cityId}")
    public ResponseEntity<List<Theater>> getTheatersByMovieCityAndDate(
            @PathVariable Long movieId,
            @PathVariable Long cityId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<Theater> theaters = theaterService.getTheatersByMovieCityAndDate(movieId, cityId, date);

        return ResponseEntity.ok(theaters);
    }

    @GetMapping("/movie/{movieId}/city/{cityId}/brand/{brandId}")
    public ResponseEntity<List<Theater>> getTheatersByMovieCityBrandAndDate(
            @PathVariable Long movieId,
            @PathVariable Long cityId,
            @PathVariable Long brandId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<Theater> theaters = theaterService.getTheatersByMovieCityBrandAndDate(movieId, cityId, brandId, date);

        return ResponseEntity.ok(theaters);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Theater> createTheater(@Valid @RequestBody Theater theater) {
        Theater savedTheater = theaterService.saveOrUpdate(null, theater);

        return ResponseEntity.ok(savedTheater);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Theater> updateTheater(
            @PathVariable Long id,
            @Valid @RequestBody Theater theater) {
        Theater updatedTheater = theaterService.saveOrUpdate(id, theater);

        return ResponseEntity.ok(updatedTheater);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheater(@PathVariable Long id) {
        theaterService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}