package com.dart.server.common.dto;

import com.dart.server.app.auth.dto.UserResponse;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public abstract class AuditDTOResponse {
    private UUID id;

    private Instant createdAt;

    private Instant updatedAt;

    private UserResponse createdByUserInfo;

    private UserResponse updatedByUserInfo;

    private boolean markedForDeletion;
}
