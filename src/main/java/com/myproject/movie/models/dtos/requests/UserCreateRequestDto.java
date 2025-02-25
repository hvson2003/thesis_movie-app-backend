package com.myproject.movie.models.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserCreateRequestDto {
    @NotNull(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotNull(message = "Password không được để trống")
    @Size(min = 6, message = "Password phải chứa ít nhất 6 ký tự")
    private String password;

    @Size(max = 50, message = "Họ và tên không được dài quá 50 ký tự")
    private String fullName;

    @NotNull(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    private LocalDate birthDate;

    private LocalDateTime createdAt = LocalDateTime.now();

    private boolean isActive = true;
}
