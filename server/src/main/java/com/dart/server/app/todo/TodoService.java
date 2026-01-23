package com.dart.server.app.todo;

import com.dart.server.app.todo.dto.TodoMapper;
import com.dart.server.app.todo.dto.TodoRequest;
import com.dart.server.app.todo.dto.TodoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public List<TodoResponse> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(TodoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Page<TodoResponse> searchTodos(String q, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return todoRepository.findByDescriptionContainingIgnoreCase(q, pageable)
                .map(TodoMapper::toResponse);
    }

    public Optional<TodoResponse> getTodoById(Long id) {
        return todoRepository.findById(id).map(TodoMapper::toResponse);
    }

    public TodoResponse createTodo(TodoRequest request) {
        TodoEntity todo = TodoMapper.toEntity(request);
        return TodoMapper.toResponse(todoRepository.save(todo));
    }

    public Optional<TodoResponse> updateTodo(Long id, TodoRequest request) {
        Optional<TodoEntity> todoOptional = todoRepository.findById(id);
        if (todoOptional.isEmpty()) {
            return Optional.empty();
        }
        TodoEntity todo = todoOptional.get();
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        return Optional.of(TodoMapper.toResponse(todoRepository.save(todo)));
    }

    public boolean deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            return false;
        }
        todoRepository.deleteById(id);
        return true;
    }
}
