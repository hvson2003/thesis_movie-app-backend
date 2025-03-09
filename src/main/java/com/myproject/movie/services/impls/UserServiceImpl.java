package com.myproject.movie.services.impls;

import com.myproject.movie.mappers.UserMapper;
import com.myproject.movie.models.dtos.requests.UserCreateRequestDto;
import com.myproject.movie.models.dtos.responses.UserReadResponseDto;
import com.myproject.movie.models.dtos.requests.UserUpdateRequestDto;
import com.myproject.movie.models.entities.User;
import com.myproject.movie.models.enums.UserRole;
import com.myproject.movie.repositories.UserRepository;
import com.myproject.movie.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public List<UserReadResponseDto> getAllUsers() {
        log.info("Fetching all active users");
        List<User> users = userRepository.findByIsActiveTrue();
        log.debug("Retrieved {} active users", users.size());

        return users.stream()
                .map(userMapper::entityToUserReadResponseDto)
                .toList();
    }

    @Override
    public User getUserEntityById(Long userId) {
        log.info("Fetching user entity with ID: {}", userId);

        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

    @Override
    public UserReadResponseDto getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        return userMapper.entityToUserReadResponseDto(user);
    }

    @Override
    @Transactional
    public UserCreateRequestDto createUser(UserCreateRequestDto userCreateRequestDto) {
        log.info("Creating user with email: {}", userCreateRequestDto.getEmail());
        validateEmailNotExists(userCreateRequestDto.getEmail());

        User user = userMapper.userCreateRequestDtoToEntity(userCreateRequestDto);
        user.setPassword(passwordEncoder.encode(userCreateRequestDto.getPassword()));
        user.setRole(UserRole.USER);

        User savedUser = userRepository.save(user);
        log.info("User created with ID: {}", savedUser.getId());

        return userMapper.entityToUserCreateRequestDto(savedUser);
    }

    @Override
    @Transactional
    public UserUpdateRequestDto updateUser(Long id, UserUpdateRequestDto userUpdateRequestDto) {
        log.info("Updating user with ID: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        userMapper.updateUserFromDto(userUpdateRequestDto, existingUser);
        User updatedUser = userRepository.save(existingUser);

        log.info("User with ID {} updated successfully", id);

        return userMapper.entityToUserUpdateRequestDto(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        log.info("User with ID {} deleted successfully", id);
    }

    private void validateEmailNotExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
    }
}