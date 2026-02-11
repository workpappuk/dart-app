package com.dart.server.app.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request payload for Role")
public class RoleRequest {
    private String name;
}