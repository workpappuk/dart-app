package com.auth.server.common.utils;

import com.auth.server.ERole;
import com.auth.server.UserEntity;
import com.auth.server.UserRepository;
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

}
