package com.dart.server.app.peddit.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {
    Page<PostEntity> findByContentContainingIgnoreCase(String content, Pageable pageable);
    Page<PostEntity> findByContentContainingIgnoreCaseAndMarkedForDeletionFalse(String content, Pageable pageable);
}

