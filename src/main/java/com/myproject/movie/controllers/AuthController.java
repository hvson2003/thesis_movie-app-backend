package com.myproject.movie.controllers;

import com.myproject.movie.models.dtos.requests.UserCreateRequestDto;
import com.myproject.movie.services.AuthService;
import com.myproject.movie.models.dtos.requests.AuthRequestDto;
import com.myproject.movie.models.dtos.responses.AuthResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(
            @RequestBody UserCreateRequestDto request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authenticate(
            @RequestBody AuthRequestDto request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
