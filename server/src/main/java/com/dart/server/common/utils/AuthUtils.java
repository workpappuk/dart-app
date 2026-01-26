package com.dart.server.common.utils;

import com.dart.server.app.auth.ERole;
import com.dart.server.app.auth.UserEntity;
import com.dart.server.app.auth.UserRepository;
import com.dart.server.app.todo.TodoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class AuthUtils {
    public static String getCurrentUsername() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                return auth.getName();
            }
        } catch (Exception e) {
            log.error("Error getting current username", e);
        }
        return null;
    }

    public static boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals(ERole.ADMIN.name()));
    }

    public static UserEntity getUser(String username, UserRepository userRepository) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public static boolean isOwner(TodoEntity todo, UserEntity user) {
        return user != null && todo.getCreatedBy() != null && todo.getCreatedBy().equals(user.getId());
    }
}
