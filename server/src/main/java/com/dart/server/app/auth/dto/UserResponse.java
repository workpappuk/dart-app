package com.dart.server.app.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Schema(description = "Response payload for User")
public class UserResponse {
    private UUID id;
    private String username;
    private Set<RoleResponse> roles;

    public UserResponse() {
    }

    public UserResponse(UUID id, String username) {
        this.id = id;
        this.username = username;
    }
}