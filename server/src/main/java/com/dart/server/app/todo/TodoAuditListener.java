package com.dart.server.app.todo;

import com.dart.server.app.auth.UserEntity;
import com.dart.server.app.auth.UserRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Setter;

import static com.dart.server.common.utils.AuthUtils.getCurrentUsername;

public class TodoAuditListener {
    @Setter
    private static UserRepository userRepository;

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
        String userId = getCurrentUsername();
        if (userId != null && userRepository != null) {
            try {
                return userRepository.findById(Long.valueOf(userId)).orElse(null);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
