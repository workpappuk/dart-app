package com.auth.server.dto;

import com.auth.server.PermissionEntity;

public class PermissionMapper {
    public static PermissionResponse toResponse(PermissionEntity entity) {
        PermissionResponse response = new PermissionResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        return response;
    }

    public static PermissionEntity toEntity(PermissionRequest request) {
        PermissionEntity entity = new PermissionEntity();
        entity.setName(request.getName());
        return entity;
    }
}