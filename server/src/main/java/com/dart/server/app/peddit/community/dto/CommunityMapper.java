package com.dart.server.app.peddit.community.dto;

import com.dart.server.app.auth.UserEntity;
import com.dart.server.app.auth.dto.UserMapper;
import com.dart.server.app.auth.dto.UserResponse;
import com.dart.server.app.peddit.community.CommunityEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CommunityMapper {
    public static CommunityEntity toEntity(CommunityRequest request) {
        CommunityEntity entity = new CommunityEntity();
        if (request == null) return entity;
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        return entity;
    }

    public static CommunityResponse toResponse(CommunityWithUser communityWithUser) {

        CommunityEntity entity = communityWithUser.entity;
        UserEntity createdBy = communityWithUser.createdBy;
        UserEntity updatedBy = communityWithUser.updatedBy;

        CommunityResponse response = new CommunityResponse();
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setId(entity.getId());
        response.setMarkedForDeletion(entity.isMarkedForDeletion());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());

        response.setCreatedUserInfo(UserMapper.toResponse(createdBy));
        response.setUpdatedUserInfo(UserMapper.toResponse(updatedBy));
        return response;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CommunityWithUser {
        CommunityEntity entity;
        UserEntity createdBy;
        UserEntity updatedBy;
    }
}
