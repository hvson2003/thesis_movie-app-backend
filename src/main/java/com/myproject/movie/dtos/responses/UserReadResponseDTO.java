package com.myproject.movie.dtos.responses;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserReadResponseDTO {
    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private boolean isActive;
}
