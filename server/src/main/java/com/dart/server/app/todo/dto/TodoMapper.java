package com.dart.server.app.todo.dto;

import com.dart.server.app.todo.TodoEntity;

public class TodoMapper {
    public static TodoResponse toResponse(TodoEntity todo) {
        TodoResponse response = new TodoResponse();
        response.setId(todo.getId());
        response.setDescription(todo.getDescription());
        response.setCompleted(todo.isCompleted());
        response.setCreatedAt(todo.getCreatedAt());
        response.setUpdatedAt(todo.getUpdatedAt());
        response.setCreatedBy(todo.getCreatedBy());
        response.setUpdatedBy(todo.getUpdatedBy());
        response.setMarkedForDeletion(todo.isMarkedForDeletion());
        return response;
    }

    public static TodoEntity toEntity(TodoRequest request) {
        TodoEntity todo = new TodoEntity();
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        return todo;
    }
}
