package com.dart.server.app.auth.dto;

import com.dart.server.app.auth.UserEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponse toResponse(UserEntity entity) {
        UserResponse response = new UserResponse();
        response.setId(entity.getId());
        response.setUsername(entity.getUsername());

        Set<RoleResponse> roleResponses = entity.getRoles()
                .stream().map(RoleMapper::toResponse)
                .collect(Collectors.toSet());

        response.setRoles(roleResponses);
        return response;
    }

    public static UserEntity toEntity(UserRequest request) {
        UserEntity entity = new UserEntity();
        entity.setUsername(request.getUsername());
        entity.setPassword(request.getPassword());
        return entity;
    }
}