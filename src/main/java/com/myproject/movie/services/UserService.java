package com.myproject.movie.services;

import com.myproject.movie.dtos.requests.UserCreateRequestDTO;
import com.myproject.movie.dtos.responses.UserReadResponseDTO;
import com.myproject.movie.dtos.responses.UserUpdateResponseDTO;
import java.util.List;

public interface UserService {
    UserCreateRequestDTO createUser(UserCreateRequestDTO userCreateRequestDTO);
    List<UserReadResponseDTO> getAllUsers();
    UserReadResponseDTO getUserById(Integer id);
    UserUpdateResponseDTO updateUser(Integer id, UserUpdateResponseDTO userUpdateResponseDTO);
    void deleteUser(Integer id);
}
