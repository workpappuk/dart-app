package com.auth.server.dto;

import com.auth.server.RoleEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {
    public static RoleResponse toResponse(RoleEntity entity) {
        RoleResponse response = new RoleResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());

        Set<PermissionResponse> permissionResponses = entity.getPermissions()
                .stream().map(PermissionMapper::toResponse)
                .collect(Collectors.toSet());

        response.setPermissions(permissionResponses);
        return response;
    }

    public static RoleEntity toEntity(RoleRequest request) {
        RoleEntity entity = new RoleEntity();
        entity.setName(request.getName());
        return entity;
    }
}