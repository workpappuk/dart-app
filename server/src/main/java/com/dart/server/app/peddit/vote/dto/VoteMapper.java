package com.dart.server.app.peddit.vote.dto;

import com.dart.server.app.peddit.vote.VoteEntity;

public class VoteMapper {
    public static VoteEntity toEntity(VoteRequest request) {
        VoteEntity entity = new VoteEntity();
        entity.setTargetId(request.getTargetId());
        entity.setTargetType(request.getTargetType());
        entity.setUpvote(request.isUpvote());
        // userId should be set in service after fetching UserEntity
        return entity;
    }

    public static VoteResponse toResponse(VoteEntity entity) {
        VoteResponse response = new VoteResponse();
        response.setId(entity.getId());
        response.setTargetId(entity.getTargetId());
        response.setTargetType(entity.getTargetType());
        response.setUserId(entity.getUser() != null ? entity.getUser().getId() : null);
        response.setUpvote(entity.isUpvote());
        return response;
    }
}

