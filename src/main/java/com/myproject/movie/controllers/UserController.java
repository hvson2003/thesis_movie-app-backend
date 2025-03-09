package com.myproject.movie.controllers;

import com.myproject.movie.models.dtos.requests.UserCreateRequestDto;
import com.myproject.movie.models.dtos.responses.UserReadResponseDto;
import com.myproject.movie.models.dtos.requests.UserUpdateRequestDto;
import com.myproject.movie.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserReadResponseDto>> getAllUsers() {
        log.info("Fetching all users");
        List<UserReadResponseDto> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserReadResponseDto> getUserById(@PathVariable Long id) {
        log.info("Fetching user with ID: {}", id);
        UserReadResponseDto user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<UserCreateRequestDto> createUser(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {
        log.info("Creating new user with email: {}", userCreateRequestDto.getEmail());
        UserCreateRequestDto createdUser = userService.createUser(userCreateRequestDto);
        log.info("User created successfully with email: {}", createdUser.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN') and @customPermissionChecker.hasAccess(authentication, #id)")
    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateRequestDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        log.info("Updating user with ID: {}", id);
        UserUpdateRequestDto updatedUser = userService.updateUser(id, userUpdateRequestDto);
        log.info("User with ID {} updated successfully", id);

        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN') and @customPermissionChecker.hasAccess(authentication, #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        log.info("User with ID {} deleted successfully", id);

        return ResponseEntity.noContent().build();
    }
}
