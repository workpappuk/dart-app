package com.dart.server.app.peddit.comment.dto;

import com.dart.server.app.peddit.comment.CommentEntity;

public class CommentMapper {
    public static CommentEntity toEntity(CommentRequest request) {
        CommentEntity entity = new CommentEntity();
        entity.setContent(request.getContent());
        entity.setTargetId(request.getTargetId());
        entity.setTargetType(request.getTargetType());
        return entity;
    }

    public static CommentResponse toResponse(CommentEntity entity) {
        CommentResponse response = new CommentResponse();
        response.setId(entity.getId());
        response.setContent(entity.getContent());
        response.setTargetId(entity.getTargetId());
        response.setTargetType(entity.getTargetType());
        response.setAuthorId(entity.getAuthor() != null ? entity.getAuthor().getId() : null);
        response.setMarkedForDeletion(entity.isMarkedForDeletion());
        return response;
    }
}

