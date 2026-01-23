package com.dart.server.app.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response payload for Permission")
public class PermissionResponse {
    private Long id;
    private String name;
}