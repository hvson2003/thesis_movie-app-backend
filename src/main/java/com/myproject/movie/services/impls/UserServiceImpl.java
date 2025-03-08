package com.myproject.movie.services.impls;

import com.myproject.movie.mappers.UserMapper;
import com.myproject.movie.models.dtos.requests.UserCreateRequestDto;
import com.myproject.movie.models.dtos.responses.UserReadResponseDto;
import com.myproject.movie.models.dtos.requests.UserUpdateRequestDto;
import com.myproject.movie.models.entities.User;
import com.myproject.movie.models.enums.UserRole;
import com.myproject.movie.repositories.UserRepository;
import com.myproject.movie.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public List<UserReadResponseDto> getAllUsers() {
        return userRepository.findByIsActiveTrue()
                .stream()
                .map(userMapper::entityToUserReadResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserEntityById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserReadResponseDto getUserById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::entityToUserReadResponseDto)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }


    @Override
    public UserCreateRequestDto createUser(UserCreateRequestDto userCreateRequestDTO) {
        User user = userMapper.userCreateRequestDtoToEntity(userCreateRequestDTO);
        user.setPassword(passwordEncoder.encode(userCreateRequestDTO.getPassword()));
        user.setRole(UserRole.USER);

        userRepository.save(user);

        return userMapper.entityToUserCreateRequestDto(user);
    }

    @Transactional
    @Override
    public UserUpdateRequestDto updateUser(Integer id, UserUpdateRequestDto userUpdateRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        userMapper.updateUserFromDto(userUpdateRequestDTO, existingUser);

        userRepository.save(existingUser);

        return userMapper.entityToUserUpdateRequestDto(existingUser);
    }

    @Transactional
    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
