package com.dart.server.app.peddit.post.dto;

import com.dart.server.app.peddit.community.CommunityEntity;
import com.dart.server.app.peddit.post.PostEntity;

public class PostMapper {
    public static PostEntity toEntity(PostRequest request, CommunityEntity community) {
        PostEntity entity = new PostEntity();
        if (request == null) {
            entity.setCommunity(community);
            return entity;
        }
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setCommunity(community);
        return entity;
    }

    public static PostResponse toResponse(PostEntity entity) {
        PostResponse response = new PostResponse();
        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setContent(entity.getContent());
        response.setCommunityId(entity.getCommunity() != null ? entity.getCommunity().getId() : null);
        response.setAuthorId(entity.getAuthor() != null ? entity.getAuthor().getId() : null);
        response.setMarkedForDeletion(entity.isMarkedForDeletion());
        return response;
    }
}
