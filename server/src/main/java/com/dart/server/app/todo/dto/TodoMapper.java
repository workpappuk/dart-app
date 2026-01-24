package com.dart.server.app.todo.dto;

import com.dart.server.app.auth.UserEntity;
import com.dart.server.app.todo.TodoEntity;

public class TodoMapper {
    public static TodoResponse toResponse(TodoEntity todo) {
        TodoResponse response = new TodoResponse();
        response.setId(todo.getId());
        response.setDescription(todo.getDescription());
        response.setCompleted(todo.isCompleted());
        response.setCreatedAt(todo.getCreatedAt());
        response.setUpdatedAt(todo.getUpdatedAt());
        response.setCreatedBy(todo.getCreatedBy() != null ? todo.getCreatedBy().getUsername() : null);
        response.setUpdatedBy(todo.getUpdatedBy() != null ? todo.getUpdatedBy().getUsername() : null);
        return response;
    }

    public static TodoEntity toEntity(TodoRequest request, UserEntity creator, UserEntity updater) {
        TodoEntity todo = new TodoEntity();
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        todo.setCreatedBy(creator);
        todo.setUpdatedBy(updater);
        return todo;
    }
}
