package com.dart.server.app.todo.dto;

import com.dart.server.app.todo.Todo;

public class TodoMapper {
    public static TodoResponse toResponse(Todo todo) {
        TodoResponse response = new TodoResponse();
        response.setId(todo.getId());
        response.setDescription(todo.getDescription());
        response.setCompleted(todo.isCompleted());
        return response;
    }

    public static Todo toEntity(TodoRequest request) {
        Todo todo = new Todo();
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        return todo;
    }
}
