package com.dart.server.app.peddit.community;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class CommunityAuditListener {
    @PrePersist
    public void prePersist(CommunityEntity entity) {
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(CommunityEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
    }
}

