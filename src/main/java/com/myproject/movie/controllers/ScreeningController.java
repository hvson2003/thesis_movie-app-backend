package com.myproject.movie.controllers;

import com.myproject.movie.models.dtos.commons.ScreeningSeatDto;
import com.myproject.movie.models.dtos.requests.BookSeatsRequestDto;
import com.myproject.movie.services.ScreeningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/screenings")
@RequiredArgsConstructor
public class ScreeningController {
    private final ScreeningService screeningService;

    @GetMapping("/{screeningId}/seats")
    public ResponseEntity<List<ScreeningSeatDto>> getSeatsByScreeningId(@PathVariable Long screeningId) {
        List<ScreeningSeatDto> seats = screeningService.getSeatsByScreeningId(screeningId);
        return ResponseEntity.ok(seats);
    }

    @PostMapping("/{screeningId}/book")
    public ResponseEntity<String> bookSeats(
            @PathVariable Long screeningId,
            @Valid @RequestBody BookSeatsRequestDto request) {
        screeningService.bookSeats(screeningId, request.getSeatIds());
        return ResponseEntity.ok("Đặt vé thành công");
    }
}
