package com.dart.server.app.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request payload for Permission")
public class PermissionRequest {
    private String name;
}