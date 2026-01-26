package com.dart.server.app.todo;

import java.util.UUID;

import com.dart.server.app.auth.ERole;
import com.dart.server.app.auth.UserEntity;
import com.dart.server.app.auth.UserRepository;
import com.dart.server.app.todo.dto.TodoMapper;
import com.dart.server.app.todo.dto.TodoRequest;
import com.dart.server.app.todo.dto.TodoResponse;
import com.dart.server.common.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    public List<TodoResponse> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(TodoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Page<TodoResponse> searchTodos(String q, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (AuthUtils.isAdmin()) {
            return todoRepository.findByDescriptionContainingIgnoreCaseAndMarkedForDeletionFalse(q, pageable)
                    .map(TodoMapper::toResponse);
        } else {
            return todoRepository.findByDescriptionContainingIgnoreCase(q, pageable)
                    .map(TodoMapper::toResponse);
        }
    }

    public Optional<TodoResponse> getTodoById(UUID id, String username) {
        Optional<TodoEntity> todoOptional = todoRepository.findByIdAndMarkedForDeletionFalse(id);
        if (todoOptional.isEmpty()) {
            return Optional.empty();
        }
        TodoEntity todo = todoOptional.get();
        UserEntity user = AuthUtils.getUser(username, userRepository);
        if (!AuthUtils.isOwner(todo, user) && !AuthUtils.isAdmin()) {
            return Optional.empty();
        }
        return Optional.of(TodoMapper.toResponse(todo));
    }

    public TodoResponse createTodo(TodoRequest request) {
        TodoEntity todo = TodoMapper.toEntity(request);
        return TodoMapper.toResponse(todoRepository.save(todo));
    }

    public Optional<TodoResponse> updateTodo(UUID id, TodoRequest request, String username) {
        Optional<TodoEntity> todoOptional = todoRepository.findById(id);
        if (todoOptional.isEmpty()) {
            return Optional.empty();
        }
        TodoEntity todo = todoOptional.get();
        UserEntity user = AuthUtils.getUser(username, userRepository);
        if (!AuthUtils.isOwner(todo, user) && !AuthUtils.isAdmin()) {
            return Optional.empty();
        }
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        return Optional.of(TodoMapper.toResponse(todoRepository.save(todo)));
    }

    public boolean deleteTodo(UUID id, String username) {
        Optional<TodoEntity> todoOptional = todoRepository.findByIdAndMarkedForDeletionFalse(id);
        if (todoOptional.isEmpty()) {
            return false;
        }
        TodoEntity todo = todoOptional.get();
        UserEntity user = AuthUtils.getUser(username, userRepository);
        if (!AuthUtils.isOwner(todo, user) && !AuthUtils.isAdmin()) {
            return false;
        }
        todo.setMarkedForDeletion(true);
        todoRepository.save(todo);
        return true;
    }
}
