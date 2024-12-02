package com.myproject.movie_booking.services;

import com.myproject.movie_booking.dtos.requests.UserCreateRequest;
import com.myproject.movie_booking.dtos.responses.UserReadResponse;
import com.myproject.movie_booking.dtos.responses.UserUpdateResponse;
import com.myproject.movie_booking.models.User;
import com.myproject.movie_booking.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserCreateRequest createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        User user = convertToEntity(userCreateRequest);
        user.setPasswordHash(passwordEncoder.encode(userCreateRequest.getPassword()));
        userRepository.save(user);

        return convertToCreateDto(user);
    }

    public List<UserReadResponse> getAllUsers() {
        return userRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToUserReadDTO)
                .collect(Collectors.toList());
    }

    public UserReadResponse getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(this::convertToUserReadDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public UserUpdateResponse updateUser(@PathVariable Long id, UserUpdateResponse userUpdateResponse) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        updateEntity(existingUser, userUpdateResponse);

        return convertToUpdateDto(userRepository.save(existingUser));
    }

    @Transactional
    public boolean deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);

        return true;
    }

    private void updateEntity(User user, UserUpdateResponse dto) {
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getFullName() != null) user.setFullName(dto.getFullName());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());
        if (dto.getBirthDate() != null) user.setBirthDate(dto.getBirthDate());
        if (dto.getIsActive() != null) user.setActive(dto.getIsActive());
    }

    private UserCreateRequest convertToCreateDto(User user) {
        UserCreateRequest userDto = new UserCreateRequest();
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setPhone(user.getPhone());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setActive(user.isActive());

        return userDto;
    }

    private User convertToEntity(UserCreateRequest userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setPhone(userDto.getPhone());
        user.setBirthDate(userDto.getBirthDate());
        user.setActive(userDto.isActive());

        return user;
    }

    private UserUpdateResponse convertToUpdateDto(User user) {
        UserUpdateResponse userUpdateResponse = new UserUpdateResponse();
        userUpdateResponse.setEmail(user.getEmail());
        userUpdateResponse.setPassword(user.getPasswordHash());
        userUpdateResponse.setFullName(user.getFullName());
        userUpdateResponse.setPhone(user.getPhone());
        userUpdateResponse.setBirthDate(user.getBirthDate());
        userUpdateResponse.setIsActive(user.isActive());

        return userUpdateResponse;
    }

    private UserReadResponse convertToUserReadDTO(User user) {
        UserReadResponse userReadResponse = new UserReadResponse();
        userReadResponse.setId(user.getId());
        userReadResponse.setEmail(user.getEmail());
        userReadResponse.setFullName(user.getFullName());
        userReadResponse.setPhone(user.getPhone());
        userReadResponse.setBirthDate(user.getBirthDate());
        userReadResponse.setActive(user.isActive());

        return userReadResponse;
    }

    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
