package com.dart.server.app.auth.dto;

import com.dart.server.app.auth.RoleEntity;

public class RoleMapper {
    public static RoleResponse toResponse(RoleEntity entity) {
        RoleResponse response = new RoleResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        return response;
    }

    public static RoleEntity toEntity(RoleRequest request) {
        RoleEntity entity = new RoleEntity();
        entity.setName(request.getName());
        return entity;
    }
}