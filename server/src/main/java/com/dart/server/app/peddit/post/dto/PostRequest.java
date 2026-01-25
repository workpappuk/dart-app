package com.dart.server.app.peddit.post.dto;

import lombok.Data;

@Data
public class PostRequest {
    private String title;
    private String content;
    private Long communityId;
}

