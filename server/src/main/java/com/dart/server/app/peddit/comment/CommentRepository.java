package com.dart.server.app.peddit.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
    Page<CommentEntity> findByContentContainingIgnoreCase(String content, Pageable pageable);
    Page<CommentEntity> findByContentContainingIgnoreCaseAndMarkedForDeletionFalse(String content, Pageable pageable);
}

