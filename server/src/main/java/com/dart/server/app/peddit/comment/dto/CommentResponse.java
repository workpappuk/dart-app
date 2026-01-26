package com.dart.server.app.peddit.comment.dto;

import com.dart.server.app.peddit.EEntityTargetType;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentResponse {
    private UUID id;
    private String content;
    private UUID targetId;
    private EEntityTargetType targetType;
    private boolean markedForDeletion;
}

