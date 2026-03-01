package com.dart.server.app.peddit.vote.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class VoteResponse {
    private UUID id;
    private UUID targetId;
    private String targetType;
    private UUID userId;
    private boolean upvote;
}

