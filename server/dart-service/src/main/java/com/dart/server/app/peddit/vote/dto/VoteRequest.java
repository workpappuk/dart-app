package com.dart.server.app.peddit.vote.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class VoteRequest {
    private UUID targetId;
    private String targetType;
    private UUID userId;
    private boolean upvote;
}
