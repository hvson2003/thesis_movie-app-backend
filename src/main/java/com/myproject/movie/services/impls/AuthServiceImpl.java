package com.myproject.movie.services.impls;

import com.myproject.movie.dtos.requests.AuthRequestDTO;
import com.myproject.movie.dtos.requests.RegisterRequestDTO;
import com.myproject.movie.dtos.responses.AuthResponseDTO;
import com.myproject.movie.models.User;
import com.myproject.movie.repositories.UserRepository;
import com.myproject.movie.services.AuthService;
import com.myproject.movie.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
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

    @Override
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
