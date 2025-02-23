package com.myproject.movie.controllers;

import com.myproject.movie.models.Theater;
import com.myproject.movie.services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theaters")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @GetMapping
    public ResponseEntity<List<Theater>> getAllTheaters() {
        return ResponseEntity.ok(theaterService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theater> getTheaterById(@PathVariable Integer id) {
        Theater theater = theaterService.findById(id);
        return ResponseEntity.ok(theater);
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
    @PatchMapping("/{id}")
    public ResponseEntity<Theater> updateTheaterPartial(
            @PathVariable Integer id,
            @RequestBody Theater updatedData
    ) {
        Theater updatedTheater = theaterService.updatePartialTheaterById(id, updatedData);
        return ResponseEntity.ok(updatedTheater);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheater(@PathVariable Integer id) {
        theaterService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
