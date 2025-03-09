package com.myproject.movie.services.impls;

import com.myproject.movie.mappers.UserMapper;
import com.myproject.movie.models.dtos.requests.AuthRequestDto;
import com.myproject.movie.models.dtos.requests.UserCreateRequestDto;
import com.myproject.movie.models.dtos.responses.AuthResponseDto;
import com.myproject.movie.models.entities.User;
import com.myproject.movie.models.enums.UserRole;
import com.myproject.movie.repositories.UserRepository;
import com.myproject.movie.services.AuthService;
import com.myproject.movie.services.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public AuthResponseDto register(UserCreateRequestDto request) {
        validateNewUser(request.getEmail());

        User user = userMapper.userCreateRequestDtoToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(savedUser);

        log.info("User registered successfully: {}", savedUser.getEmail());
        return new AuthResponseDto(jwtToken);
    }

    @Override
    public AuthResponseDto authenticate(AuthRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            log.warn("Authentication failed for email: {}", request.getEmail());
            throw new IllegalArgumentException("Invalid email or password");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("User not found after authentication"));
        String jwtToken = jwtService.generateToken(user);

        log.info("User authenticated successfully: {}", user.getEmail());
        return new AuthResponseDto(jwtToken);
    }

    private void validateNewUser(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
    }
}
