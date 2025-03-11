package com.myproject.movie.utils;

import com.myproject.movie.models.entities.User;
import com.myproject.movie.models.enums.UserRole;
import com.myproject.movie.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomPermissionChecker {
    private final UserRepository userRepository;

    public boolean hasAccess(Authentication authentication, Long userId) {
        String email = authentication.getName();
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) { return false; }
        User userEntity = user.get();
        if (userEntity.getRole().equals(UserRole.ADMIN)) { return true; }

        return checkUserPermission(userEntity, userId);
    }

    private boolean checkUserPermission(User user, Long userId) {
        return user.getId().equals(userId);
    }
}
