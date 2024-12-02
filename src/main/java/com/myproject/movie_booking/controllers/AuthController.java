package com.myproject.movie_booking.controllers;

import com.myproject.movie_booking.services.AuthService;
import com.myproject.movie_booking.dtos.requests.AuthRequest;
import com.myproject.movie_booking.dtos.requests.RegisterRequest;
import com.myproject.movie_booking.dtos.responses.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
