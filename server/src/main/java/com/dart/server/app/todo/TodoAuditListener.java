package com.dart.server.app.todo;

import com.dart.server.app.auth.UserEntity;
import com.dart.server.app.auth.UserRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configurable
public class TodoAuditListener {
    private static UserRepository userRepository;

    @Autowired
    public static void setUserRepository(UserRepository repo) {
        userRepository = repo;
    }

    @PrePersist
    public void setCreatedBy(TodoEntity entity) {
        UserEntity user = getCurrentUserEntity();
        if (user != null) {
            entity.setCreatedBy(user);
            entity.setUpdatedBy(user);
        }
    }

    @PreUpdate
    public void setUpdatedBy(TodoEntity entity) {
        UserEntity user = getCurrentUserEntity();
        if (user != null) {
            entity.setUpdatedBy(user);
        }
    }

    private UserEntity getCurrentUserEntity() {
        String username = getCurrentUsername();
        if (username != null && userRepository != null) {
            return userRepository.findById(Long.valueOf(username)).orElse(null);
        }
        return null;
    }

    private String getCurrentUsername() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                return auth.getName();
            }
        } catch (Exception ignored) {

        }
        return null;
    }
}
