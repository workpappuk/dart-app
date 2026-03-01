package com.dart.server.app.peddit.post;

import com.dart.server.app.auth.UserEntity;
import com.dart.server.app.peddit.community.CommunityEntity;
import com.dart.server.common.db.Auditable;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "posts")
public class PostEntity extends Auditable {

    @Column(nullable = false)
    private String title;

    @Column(length = 4096)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)
    private CommunityEntity community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

}
