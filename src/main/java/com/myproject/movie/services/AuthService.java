package com.myproject.movie.services;

import com.myproject.movie.dtos.requests.AuthRequestDTO;
import com.myproject.movie.dtos.requests.RegisterRequestDTO;
import com.myproject.movie.dtos.responses.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterRequestDTO request);
    AuthResponseDTO authenticate(AuthRequestDTO request);
}