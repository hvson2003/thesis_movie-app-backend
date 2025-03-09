package com.myproject.movie.services;

import com.myproject.movie.models.entities.User;
import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String generateToken(User user);
    String generateToken(Map<String, Object> extraClaims, User user);
    String extractUsername(String token);
    Long extractUserId(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    boolean isTokenValid(String token, User user);
    Claims extractAllClaims(String token);
    boolean isTokenExpired(String token);
}