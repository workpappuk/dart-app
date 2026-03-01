package com.dart.server.app.peddit.post.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PostRequest {
    private String title;
    private String content;
    private UUID communityId;
}

