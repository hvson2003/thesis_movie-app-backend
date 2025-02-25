package com.myproject.movie.models.dtos.responses;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserReadResponseDto {
    private Integer id;
    private String email;
    private String fullName;
    private String phone;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private boolean isActive;
}
