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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserReadResponseDto>> getAllUsers() {
        log.info("Fetching all users");

        return ResponseEntity.ok(userService.getAllUsers());
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserReadResponseDto> getUserById(@PathVariable Integer id) {
        log.info("Fetching user with ID: {}", id);

        return ResponseEntity.ok(userService.getUserById(id));
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<UserCreateRequestDto> createUser(@Valid @RequestBody UserCreateRequestDto userCreateRequestDTO) {
        log.info("Creating new user: {}", userCreateRequestDTO.getEmail());
        UserCreateRequestDto createdUser = userService.createUser(userCreateRequestDTO);
        log.info("User created successfully: {}", createdUser.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

//    @PreAuthorize("hasAnyRole('USER', 'ADMIN') and @customPermissionChecker.hasAccess(authentication, #id)")
    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateRequestDto> updateUser(@PathVariable Integer id, @RequestBody UserUpdateRequestDto userUpdateRequestDTO) {
        log.info("Updating user with ID: {}", id);
        UserUpdateRequestDto updatedUser = userService.updateUser(id, userUpdateRequestDTO);
        log.info("User with ID {} updated successfully", id);

        return ResponseEntity.ok(updatedUser);    }

//    @PreAuthorize("hasAnyRole('USER', 'ADMIN') and @customPermissionChecker.hasAccess(authentication, #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        log.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        log.info("User with ID {} deleted successfully", id);

        return ResponseEntity.noContent().build();
    }
}