package com.dart.server.app.peddit.community.dto;

import com.dart.server.app.peddit.community.CommunityEntity;

public class CommunityMapper {
    public static CommunityEntity toEntity(CommunityRequest request) {
        CommunityEntity entity = new CommunityEntity();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        return entity;
    }

    public static CommunityResponse toResponse(CommunityEntity entity) {
        CommunityResponse response = new CommunityResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setCreatedBy(entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        response.setMarkedForDeletion(entity.isMarkedForDeletion());
        return response;
    }
}

