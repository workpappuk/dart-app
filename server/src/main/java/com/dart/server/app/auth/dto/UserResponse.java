package com.dart.server.app.auth.dto;

import java.util.Set;

import com.dart.server.app.auth.RoleEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response payload for User")
public class UserResponse {
    private Long id;
    private String username;
    private Set<RoleResponse> roles;
}