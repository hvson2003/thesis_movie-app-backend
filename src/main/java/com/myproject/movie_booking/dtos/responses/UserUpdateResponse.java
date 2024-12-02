package com.myproject.movie_booking.dtos.responses;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UserUpdateResponse {
    @Email(message = "Email không đúng định dạng")
    private String email;

    @Size(min = 6, message = "Password phải chứa ít nhất 6 ký tự")
    private String password;

    @Size(max = 50, message = "Họ và tên không được dài quá 50 ký tự")
    private String fullName;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    private LocalDate birthDate;

    private Boolean isActive;
}
