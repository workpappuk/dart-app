package com.dart.server.app.peddit.community;

import com.dart.server.app.auth.UserEntity;
import com.dart.server.app.peddit.post.PostEntity;
import com.dart.server.common.db.Auditable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "communities")
public class CommunityEntity extends Auditable {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 1024)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "community_members",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> members = new HashSet<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostEntity> posts = new HashSet<>();

}
