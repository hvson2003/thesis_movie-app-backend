package com.myproject.movie.services;

import com.myproject.movie.models.dtos.requests.AuthRequestDto;
import com.myproject.movie.models.dtos.requests.UserCreateRequestDto;
import com.myproject.movie.models.dtos.responses.AuthResponseDto;

public interface AuthService {
    AuthResponseDto register(UserCreateRequestDto request);
    AuthResponseDto authenticate(AuthRequestDto request);
}