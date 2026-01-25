package com.dart.server.app.peddit.post.dto;

import lombok.Data;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Long communityId;
    private Long authorId;
    private boolean markedForDeletion;
}

