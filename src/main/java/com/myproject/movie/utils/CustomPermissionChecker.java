package com.myproject.movie.utils;

import com.myproject.movie.models.User;
import com.myproject.movie.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomPermissionChecker {

    private final UserRepository userRepository;

    public CustomPermissionChecker(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean hasAccess(Authentication authentication, Integer userId) {
        String email = authentication.getName();
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) { return false; }
        User userEntity = user.get();
        if (userEntity.getRole().equals(User.Role.ADMIN)) { return true; }

        return checkUserPermission(userEntity, userId);
    }

    private boolean checkUserPermission(User user, Integer userId) {
        return user.getId().equals(userId);
    }
}
