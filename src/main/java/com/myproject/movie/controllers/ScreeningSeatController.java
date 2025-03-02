package com.myproject.movie.controllers;

import com.myproject.movie.models.dtos.commons.ScreeningSeatDto;
import com.myproject.movie.services.ScreeningSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/screenings")
@RequiredArgsConstructor
public class ScreeningSeatController {
    private final ScreeningSeatService screeningSeatService;

    @GetMapping("/{screeningId}/seats")
    public ResponseEntity<List<ScreeningSeatDto>> getSeatsByScreeningId(@PathVariable Integer screeningId) {
        List<ScreeningSeatDto> seats = screeningSeatService.getSeatsByScreeningId(screeningId);
        if (seats.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(seats);
    }
}
