package com.dart.server.app.peddit.comment.dto;

import com.dart.server.app.peddit.EEntityTargetType;
import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private String content;
    private Long targetId;
    private EEntityTargetType targetType;
    private Long authorId;
    private boolean markedForDeletion;
}

