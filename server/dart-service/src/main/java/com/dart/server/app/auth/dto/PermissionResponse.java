package com.dart.server.app.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Response payload for Permission")
public class PermissionResponse {
    private UUID id;
    private String name;
}