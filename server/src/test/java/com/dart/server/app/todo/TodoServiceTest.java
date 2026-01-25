package com.dart.server.app.todo;

import com.dart.server.app.auth.UserEntity;
import com.dart.server.app.auth.UserRepository;
import com.dart.server.app.todo.dto.TodoRequest;
import com.dart.server.app.todo.dto.TodoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    @Mock
    TodoRepository todoRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    TodoService todoService;

    @BeforeEach
    void setUp() {
        // ...existing code...
    }

    @Test
    void contextLoads() {
        // Use the fields to avoid warnings
        assertNotNull(todoRepository);
        assertNotNull(userRepository);
        assertNotNull(todoService);
    }

    @Test
    void getAllTodos_shouldReturnList() {
        when(todoRepository.findAll()).thenReturn(Collections.emptyList());
        List<TodoResponse> result = todoService.getAllTodos();
        assertNotNull(result);
    }

    @Test
    void createTodo_shouldReturnResponse() {
        TodoRequest req = new TodoRequest();
        TodoEntity entity = new TodoEntity();
        when(todoRepository.save(any())).thenReturn(entity);
        TodoResponse result = todoService.createTodo(req, "user");
        assertNotNull(result);
    }

    @Test
    void updateTodo_shouldReturnUpdated_whenAdmin() {
        TodoEntity entity = new TodoEntity();
        entity.setId(1L);
        UserEntity user = new UserEntity();
        user.setId(2L);
        UserEntity creator = new UserEntity();
        creator.setId(3L);
        entity.setCreatedBy(creator); // Ensure createdBy is not null and has an id
        TodoRequest req = new TodoRequest();
        req.setDescription("desc");
        req.setCompleted(true);
        when(todoRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(todoRepository.save(any())).thenReturn(entity);
        // Set up SecurityContext for admin
        Authentication auth = new UsernamePasswordAuthenticationToken("admin", null, Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
        Optional<TodoResponse> result = todoService.updateTodo(1L, req, "admin");
        assertTrue(result.isPresent());
    }

    @Test
    void updateTodo_shouldReturnEmpty_whenNotOwnerOrAdmin() {
        TodoEntity entity = new TodoEntity();
        entity.setId(1L);
        UserEntity user = new UserEntity();
        user.setId(2L);
        UserEntity creator = new UserEntity();
        creator.setId(3L);
        entity.setCreatedBy(creator); // Ensure createdBy is not null and has an id
        TodoRequest req = new TodoRequest();
        req.setDescription("desc");
        req.setCompleted(true);
        when(todoRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        // Set up SecurityContext for non-owner, non-admin
        Authentication auth = new UsernamePasswordAuthenticationToken("user", null, Collections.singletonList(new SimpleGrantedAuthority("USER")));
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
        Optional<TodoResponse> result = todoService.updateTodo(1L, req, "user");
        assertTrue(result.isEmpty());
    }

    @Test
    void updateTodo_shouldReturnEmpty_whenNotFound() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());
        TodoRequest req = new TodoRequest();
        Optional<TodoResponse> result = todoService.updateTodo(1L, req, "user");
        assertTrue(result.isEmpty());
    }

}
