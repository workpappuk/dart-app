package com.dart.server.app.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    Page<TodoEntity> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    Page<TodoEntity> findByDescriptionContainingIgnoreCaseAndCreatedBy_Username(String description, String username, Pageable pageable);

    Page<TodoEntity> findByDescriptionContainingIgnoreCaseAndMarkedForDeletionFalse(String description, Pageable pageable);

    Page<TodoEntity> findByDescriptionContainingIgnoreCaseAndCreatedBy_UsernameAndMarkedForDeletionFalse(String description, String username, Pageable pageable);

    Optional<TodoEntity> findByIdAndMarkedForDeletionFalse(Long id);
}
