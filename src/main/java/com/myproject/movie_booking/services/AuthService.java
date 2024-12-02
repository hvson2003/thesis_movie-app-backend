package com.myproject.movie_booking.services;

import com.myproject.movie_booking.dtos.requests.AuthRequestDTO;
import com.myproject.movie_booking.dtos.requests.RegisterRequestDTO;
import com.myproject.movie_booking.dtos.responses.AuthResponseDTO;
import com.myproject.movie_booking.models.User;
import com.myproject.movie_booking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(RegisterRequestDTO request) {
        var user = User
                .builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .birthDate(request.getBirthDate())
                .role(User.Role.USER)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponseDTO.builder()
                .token(jwtToken)
                .build();
    }
}
