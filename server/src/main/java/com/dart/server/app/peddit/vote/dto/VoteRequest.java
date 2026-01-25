package com.dart.server.app.peddit.vote.dto;

import lombok.Data;

@Data
public class VoteRequest {
    private Long targetId;
    private String targetType;
    private Long userId;
    private boolean upvote;
}
