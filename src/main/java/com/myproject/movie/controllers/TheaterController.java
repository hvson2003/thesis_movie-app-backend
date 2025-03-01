package com.myproject.movie.controllers;

import com.myproject.movie.models.entities.Theater;
import com.myproject.movie.services.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/theaters")
public class TheaterController {
    private final TheaterService theaterService;

    @GetMapping
    public ResponseEntity<List<Theater>> getAllTheaters() {
        return ResponseEntity.ok(theaterService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theater> getTheaterById(@PathVariable Integer id) {
        Theater theater = theaterService.findById(id);
        return ResponseEntity.ok(theater);
    }

    @GetMapping("/movie/{movieId}/city/{cityId}/brand/{brandId}/date")
    public ResponseEntity<List<Theater>> getTheatersByMovieCityBrandAndDate(
            @PathVariable Integer movieId,
            @PathVariable Integer cityId,
            @PathVariable Integer brandId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        List<Theater> theaters = theaterService.getTheatersByMovieCityBrandAndDate(movieId, cityId, brandId, date);
        return ResponseEntity.ok(theaters);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Theater> createTheater(@RequestBody Theater theater) {
        Theater savedTheater = theaterService.saveOrUpdate(null, theater);

        return ResponseEntity.ok(savedTheater);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Theater> updateTheater(
            @PathVariable Integer id,
            @RequestBody Theater updatedTheater
    ) {
        Theater updated = theaterService.saveOrUpdate(id, updatedTheater);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheater(@PathVariable Integer id) {
        theaterService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
