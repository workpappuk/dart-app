package com.dart.server.app.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    Page<TodoEntity> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

}
