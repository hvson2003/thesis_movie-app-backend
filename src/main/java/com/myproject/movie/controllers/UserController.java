package com.myproject.movie.controllers;

import com.myproject.movie.dtos.requests.UserCreateRequestDTO;
import com.myproject.movie.dtos.responses.UserReadResponseDTO;
import com.myproject.movie.dtos.responses.UserUpdateResponseDTO;
import com.myproject.movie.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<UserCreateRequestDTO> createUser(@Valid @RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        log.info("Creating new user: {}", userCreateRequestDTO.getEmail());
        UserCreateRequestDTO createdUser = userService.createUser(userCreateRequestDTO);
        log.info("User created successfully: {}", createdUser.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserReadResponseDTO>> getAllUsers() {
        log.info("Fetching all users");

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserReadResponseDTO> getUserById(@PathVariable Long id) {
        log.info("Fetching user with ID: {}", id);

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN') and @customPermissionChecker.hasAccess(authentication, #id)")
    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateResponseDTO userUpdateResponseDTO) {
        log.info("Updating user with ID: {}", id);
        UserUpdateResponseDTO updatedUser = userService.updateUser(id, userUpdateResponseDTO);
        log.info("User with ID {} updated successfully", id);

        return ResponseEntity.ok(updatedUser);    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN') and @customPermissionChecker.hasAccess(authentication, #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        log.info("User with ID {} deleted successfully", id);

        return ResponseEntity.noContent().build();
    }
}