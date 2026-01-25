package com.dart.server.app.peddit.vote.dto;

import lombok.Data;

@Data
public class VoteResponse {
    private Long id;
    private Long targetId;
    private String targetType;
    private Long userId;
    private boolean upvote;
}

