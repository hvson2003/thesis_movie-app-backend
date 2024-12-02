package com.myproject.movie_booking.dtos.requests;

import com.myproject.movie_booking.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private LocalDate birthDate;
    private User.Role role;
    private LocalDateTime createdAt = LocalDateTime.now();
}
