package com.dart.server.app.auth.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Response payload for Role")
public class RoleResponse {
    private UUID id;
    private String name;
    private java.util.Set<PermissionResponse> permissions;

}