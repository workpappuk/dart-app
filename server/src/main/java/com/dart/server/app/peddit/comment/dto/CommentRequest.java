package com.dart.server.app.peddit.comment.dto;

import com.dart.server.app.peddit.EEntityTargetType;
import lombok.Data;

@Data
public class CommentRequest {
    private String content;
    private Long targetId;
    private EEntityTargetType targetType;
}

