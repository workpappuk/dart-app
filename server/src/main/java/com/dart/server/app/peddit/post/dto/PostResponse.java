package com.dart.server.app.peddit.post.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PostResponse {
    private UUID id;
    private String title;
    private String content;
    private UUID communityId;
    private UUID authorId;
    private boolean markedForDeletion;
}

