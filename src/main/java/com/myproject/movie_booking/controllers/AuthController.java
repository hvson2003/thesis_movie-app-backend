package com.myproject.movie_booking.controllers;

import com.myproject.movie_booking.services.AuthService;
import com.myproject.movie_booking.dtos.requests.AuthRequestDTO;
import com.myproject.movie_booking.dtos.requests.RegisterRequestDTO;
import com.myproject.movie_booking.dtos.responses.AuthResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @RequestBody RegisterRequestDTO request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticate(
            @RequestBody AuthRequestDTO request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
