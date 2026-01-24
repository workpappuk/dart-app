package com.dart.server.app.todo;

import com.dart.server.app.auth.UserEntity;
import com.dart.server.app.auth.UserRepository;
import com.dart.server.app.todo.dto.TodoMapper;
import com.dart.server.app.todo.dto.TodoRequest;
import com.dart.server.app.todo.dto.TodoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
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
        return todoRepository.findByDescriptionContainingIgnoreCase(q, pageable)
                .map(TodoMapper::toResponse);
    }

    public Optional<TodoResponse> getTodoById(Long id) {
        return todoRepository.findById(id).map(TodoMapper::toResponse);
    }

    public TodoResponse createTodo(TodoRequest request, String username) {
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        TodoEntity todo = TodoMapper.toEntity(request, user, user);
        return TodoMapper.toResponse(todoRepository.save(todo));
    }

    public Optional<TodoResponse> updateTodo(Long id, TodoRequest request, String username) {
        Optional<TodoEntity> todoOptional = todoRepository.findById(id);
        if (todoOptional.isEmpty()) {
            return Optional.empty();
        }
        TodoEntity todo = todoOptional.get();
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        boolean isOwner = user != null && todo.getCreatedBy() != null && todo.getCreatedBy().getId().equals(user.getId());
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
        if (!isOwner && !isAdmin) {
            return Optional.empty();
        }
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        if (user != null) {
            todo.setUpdatedBy(user);
        }
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
