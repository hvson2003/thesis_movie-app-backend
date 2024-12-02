package com.myproject.movie_booking.services;

import com.myproject.movie_booking.dtos.requests.UserCreateRequestDTO;
import com.myproject.movie_booking.dtos.responses.UserReadResponseDTO;
import com.myproject.movie_booking.dtos.responses.UserUpdateResponseDTO;
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

    public UserCreateRequestDTO createUser(@Valid @RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        User user = convertToEntity(userCreateRequestDTO);
        user.setPasswordHash(passwordEncoder.encode(userCreateRequestDTO.getPassword()));
        userRepository.save(user);

        return convertToCreateDto(user);
    }

    public List<UserReadResponseDTO> getAllUsers() {
        return userRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToUserReadDTO)
                .collect(Collectors.toList());
    }

    public UserReadResponseDTO getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(this::convertToUserReadDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public UserUpdateResponseDTO updateUser(@PathVariable Long id, UserUpdateResponseDTO userUpdateResponseDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        updateEntity(existingUser, userUpdateResponseDTO);

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

    private void updateEntity(User user, UserUpdateResponseDTO dto) {
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getFullName() != null) user.setFullName(dto.getFullName());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());
        if (dto.getBirthDate() != null) user.setBirthDate(dto.getBirthDate());
        if (dto.getIsActive() != null) user.setActive(dto.getIsActive());
    }

    private UserCreateRequestDTO convertToCreateDto(User user) {
        UserCreateRequestDTO userDto = new UserCreateRequestDTO();
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setPhone(user.getPhone());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setActive(user.isActive());

        return userDto;
    }

    private User convertToEntity(UserCreateRequestDTO userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setPhone(userDto.getPhone());
        user.setBirthDate(userDto.getBirthDate());
        user.setActive(userDto.isActive());

        return user;
    }

    private UserUpdateResponseDTO convertToUpdateDto(User user) {
        UserUpdateResponseDTO userUpdateResponseDTO = new UserUpdateResponseDTO();
        userUpdateResponseDTO.setEmail(user.getEmail());
        userUpdateResponseDTO.setPassword(user.getPasswordHash());
        userUpdateResponseDTO.setFullName(user.getFullName());
        userUpdateResponseDTO.setPhone(user.getPhone());
        userUpdateResponseDTO.setBirthDate(user.getBirthDate());
        userUpdateResponseDTO.setIsActive(user.isActive());

        return userUpdateResponseDTO;
    }

    private UserReadResponseDTO convertToUserReadDTO(User user) {
        UserReadResponseDTO userReadResponseDTO = new UserReadResponseDTO();
        userReadResponseDTO.setId(user.getId());
        userReadResponseDTO.setEmail(user.getEmail());
        userReadResponseDTO.setFullName(user.getFullName());
        userReadResponseDTO.setPhone(user.getPhone());
        userReadResponseDTO.setBirthDate(user.getBirthDate());
        userReadResponseDTO.setActive(user.isActive());

        return userReadResponseDTO;
    }

    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
