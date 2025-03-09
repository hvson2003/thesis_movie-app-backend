package com.myproject.movie.services;

import com.myproject.movie.models.dtos.requests.UserCreateRequestDto;
import com.myproject.movie.models.dtos.responses.UserReadResponseDto;
import com.myproject.movie.models.dtos.requests.UserUpdateRequestDto;
import com.myproject.movie.models.entities.User;

import java.util.List;

public interface UserService {
    UserCreateRequestDto createUser(UserCreateRequestDto userCreateRequestDTO);
    List<UserReadResponseDto> getAllUsers();
    User getUserEntityById(Long userId);
    UserReadResponseDto getUserById(Long id);
    UserUpdateRequestDto updateUser(Long id, UserUpdateRequestDto userUpdateRequestDTO);
    void deleteUser(Long id);
}
