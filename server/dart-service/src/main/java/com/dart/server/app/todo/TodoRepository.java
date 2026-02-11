package com.dart.server.app.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TodoRepository extends JpaRepository<TodoEntity, UUID> {

    Page<TodoEntity> findByDescriptionContainingIgnoreCaseAndMarkedForDeletionFalse(String description, Pageable pageable);

    Page<TodoEntity> findByDescriptionContainingIgnoreCase(
            String description, Pageable pageable
    );

    Optional<TodoEntity> findByIdAndMarkedForDeletionFalse(UUID id);
}
