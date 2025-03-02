package com.myproject.movie.controllers;

import com.myproject.movie.models.dtos.commons.ScreeningSeatDto;
import com.myproject.movie.models.dtos.requests.BookSeatsRequestDto;
import com.myproject.movie.services.ScreeningSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{screeningId}/book")
    public ResponseEntity<String> bookSeats(
            @PathVariable Integer screeningId,
            @RequestBody BookSeatsRequestDto request
    ) {
        try {
            screeningSeatService.bookSeats(screeningId, request.getSeatIds());
            return ResponseEntity.ok("Đặt vé thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }
}
