package com.dart.server.common.dto;

import com.dart.server.app.auth.dto.UserResponse;
import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.UUID;

@Data
public abstract class AuditDTOResponse {
    private UUID id;

    private Instant createdAt;

    private Instant updatedAt;

    private UserResponse createdUserInfo;

    private UserResponse updatedUserInfo;

    private boolean markedForDeletion;
}
