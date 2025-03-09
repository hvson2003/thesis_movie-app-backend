package com.myproject.movie.models.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequestDto {
    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Size(max = 50, message = "Full name must not exceed 50 characters")
    private String fullName;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phone;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    private Boolean isActive;
}
