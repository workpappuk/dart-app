package com.dart.server.app.peddit.comment;

import com.dart.server.app.peddit.EEntityTargetType;
import com.dart.server.common.db.Auditable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "comments")
public class CommentEntity extends Auditable {

    @Column(length = 2048, nullable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    private UUID targetId;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private EEntityTargetType targetType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private CommentEntity parentComment;

}
