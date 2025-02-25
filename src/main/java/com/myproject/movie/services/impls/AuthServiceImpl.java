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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    public AuthResponseDto register(UserCreateRequestDto request) {
        User user = userMapper.userCreateRequestDtoToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return new AuthResponseDto(jwtToken);
    }

    @Override
    public AuthResponseDto authenticate(AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return new AuthResponseDto(jwtToken);
    }
}
