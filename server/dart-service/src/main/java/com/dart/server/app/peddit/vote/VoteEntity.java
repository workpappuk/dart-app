package com.dart.server.app.peddit.vote;

import com.dart.server.app.auth.UserEntity;
import com.dart.server.common.db.Auditable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "votes")
public class VoteEntity extends Auditable {

    @Column(nullable = false)
    private UUID targetId;

    @Column(nullable = false)
    private String targetType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private boolean upvote;
}