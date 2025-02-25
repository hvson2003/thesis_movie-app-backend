package com.myproject.movie.services;

import com.myproject.movie.models.dtos.requests.UserCreateRequestDto;
import com.myproject.movie.models.dtos.responses.UserReadResponseDto;
import com.myproject.movie.models.dtos.requests.UserUpdateRequestDto;
import java.util.List;

public interface UserService {
    UserCreateRequestDto createUser(UserCreateRequestDto userCreateRequestDTO);
    List<UserReadResponseDto> getAllUsers();
    UserReadResponseDto getUserById(Integer id);
    UserUpdateRequestDto updateUser(Integer id, UserUpdateRequestDto userUpdateRequestDTO);
    void deleteUser(Integer id);
}
