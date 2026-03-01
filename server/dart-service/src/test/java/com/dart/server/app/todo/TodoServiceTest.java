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
import java.util.UUID;

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
        TodoResponse result = todoService.createTodo(req);
        assertNotNull(result);
    }

    @Test
    void updateTodo_shouldReturnUpdated_whenAdmin() {
        UUID todoId = UUID.randomUUID();
        TodoEntity entity = new TodoEntity();
        entity.setId(todoId);
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        UserEntity creator = new UserEntity();
        creator.setId(UUID.randomUUID());
        TodoRequest req = new TodoRequest();
        req.setDescription("desc");
        req.setCompleted(true);
        when(todoRepository.findById(todoId)).thenReturn(Optional.of(entity));
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(todoRepository.save(any())).thenReturn(entity);
        // Set up SecurityContext for admin
        Authentication auth = new UsernamePasswordAuthenticationToken("admin", null, Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
        Optional<TodoResponse> result = todoService.updateTodo(todoId, req, "admin");
        assertTrue(result.isPresent());
    }

    @Test
    void updateTodo_shouldReturnEmpty_whenNotOwnerOrAdmin() {
        UUID todoId = UUID.randomUUID();
        TodoEntity entity = new TodoEntity();
        entity.setId(todoId);
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        UserEntity creator = new UserEntity();
        creator.setId(UUID.randomUUID());
        TodoRequest req = new TodoRequest();
        req.setDescription("desc");
        req.setCompleted(true);
        when(todoRepository.findById(todoId)).thenReturn(Optional.of(entity));
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        // Set up SecurityContext for non-owner, non-admin
        Authentication auth = new UsernamePasswordAuthenticationToken("user", null, Collections.singletonList(new SimpleGrantedAuthority("USER")));
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
        Optional<TodoResponse> result = todoService.updateTodo(todoId, req, "user");
        assertTrue(result.isEmpty());
    }

    @Test
    void updateTodo_shouldReturnEmpty_whenNotFound() {
        UUID todoId = UUID.randomUUID();
        when(todoRepository.findById(todoId)).thenReturn(Optional.empty());
        TodoRequest req = new TodoRequest();
        Optional<TodoResponse> result = todoService.updateTodo(todoId, req, "user");
        assertTrue(result.isEmpty());
    }

    @Test
    void searchTodos_shouldReturnPage_admin() {
        try (var mocked = mockStatic(com.dart.server.common.utils.AuthUtils.class)) {
            mocked.when(com.dart.server.common.utils.AuthUtils::isAdmin).thenReturn(true);
            mocked.when(() -> com.dart.server.common.utils.AuthUtils.getUser(any(), any())).thenReturn(new UserEntity());
            var page = new org.springframework.data.domain.PageImpl<>(Collections.singletonList(new TodoEntity()));
            when(todoRepository.findByDescriptionContainingIgnoreCaseAndMarkedForDeletionFalse(any(), any())).thenReturn(page);
            var result = todoService.searchTodos("test", 0, 10);
            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
        }
    }

    @Test
    void searchTodos_shouldReturnPage_user() {
        try (var mocked = mockStatic(com.dart.server.common.utils.AuthUtils.class)) {
            mocked.when(com.dart.server.common.utils.AuthUtils::isAdmin).thenReturn(false);
            mocked.when(() -> com.dart.server.common.utils.AuthUtils.getUser(any(), any())).thenReturn(new UserEntity());
            var page = new org.springframework.data.domain.PageImpl<>(Collections.singletonList(new TodoEntity()));
            when(todoRepository.findByDescriptionContainingIgnoreCase(any(), any())).thenReturn(page);
            var result = todoService.searchTodos("test", 0, 10);
            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
        }
    }

    @Test
    void getTodoById_shouldReturnEmpty_whenNotOwnerOrAdmin() {
        UUID todoId = UUID.randomUUID();
        TodoEntity todo = new TodoEntity();
        UserEntity user = new UserEntity();
        try (var mocked = mockStatic(com.dart.server.common.utils.AuthUtils.class)) {
            mocked.when(() -> com.dart.server.common.utils.AuthUtils.getUser(any(), any())).thenReturn(user);
            mocked.when(() -> com.dart.server.common.utils.AuthUtils.isOwner(any(), any())).thenReturn(false);
            mocked.when(com.dart.server.common.utils.AuthUtils::isAdmin).thenReturn(false);
            when(todoRepository.findByIdAndMarkedForDeletionFalse(todoId)).thenReturn(Optional.of(todo));
            var result = todoService.getTodoById(todoId, "user");
            assertTrue(result.isEmpty());
        }
    }

    @Test
    void getTodoById_shouldReturn_whenOwner() {
        UUID todoId = UUID.randomUUID();
        TodoEntity todo = new TodoEntity();
        UserEntity user = new UserEntity();
        try (var mocked = mockStatic(com.dart.server.common.utils.AuthUtils.class)) {
            mocked.when(() -> com.dart.server.common.utils.AuthUtils.getUser(any(), any())).thenReturn(user);
            mocked.when(() -> com.dart.server.common.utils.AuthUtils.isOwner(any(), any())).thenReturn(true);
            mocked.when(com.dart.server.common.utils.AuthUtils::isAdmin).thenReturn(false);
            when(todoRepository.findByIdAndMarkedForDeletionFalse(todoId)).thenReturn(Optional.of(todo));
            var result = todoService.getTodoById(todoId, "user");
            assertTrue(result.isPresent());
        }
    }

    @Test
    void getTodoById_shouldReturn_whenAdmin() {
        UUID todoId = UUID.randomUUID();
        TodoEntity todo = new TodoEntity();
        UserEntity user = new UserEntity();
        try (var mocked = mockStatic(com.dart.server.common.utils.AuthUtils.class)) {
            mocked.when(() -> com.dart.server.common.utils.AuthUtils.getUser(any(), any())).thenReturn(user);
            mocked.when(() -> com.dart.server.common.utils.AuthUtils.isOwner(any(), any())).thenReturn(false);
            mocked.when(com.dart.server.common.utils.AuthUtils::isAdmin).thenReturn(true);
            when(todoRepository.findByIdAndMarkedForDeletionFalse(todoId)).thenReturn(Optional.of(todo));
            var result = todoService.getTodoById(todoId, "user");
            assertTrue(result.isPresent());
        }
    }

    @Test
    void createTodo_shouldReturnResponse_whenUserNull() {
        TodoRequest req = new TodoRequest();
        TodoEntity entity = new TodoEntity();
        try (var mocked = mockStatic(com.dart.server.common.utils.AuthUtils.class)) {
            mocked.when(() -> com.dart.server.common.utils.AuthUtils.getUser(any(), any())).thenReturn(null);
            when(todoRepository.save(any())).thenReturn(entity);
            TodoResponse result = todoService.createTodo(req);
            assertNotNull(result);
        }
    }

    @Test
    void deleteTodo_shouldReturnFalse_whenNotOwnerOrAdmin() {
        UUID todoId = UUID.randomUUID();
        TodoEntity todo = new TodoEntity();
        UserEntity user = new UserEntity();
        try (var mocked = mockStatic(com.dart.server.common.utils.AuthUtils.class)) {
            mocked.when(() -> com.dart.server.common.utils.AuthUtils.getUser(any(), any())).thenReturn(user);
            mocked.when(() -> com.dart.server.common.utils.AuthUtils.isOwner(any(), any())).thenReturn(false);
            mocked.when(com.dart.server.common.utils.AuthUtils::isAdmin).thenReturn(false);
            when(todoRepository.findByIdAndMarkedForDeletionFalse(todoId)).thenReturn(Optional.of(todo));
            boolean result = todoService.deleteTodo(todoId, "user");
            assertFalse(result);
        }
    }

    @Test
    void deleteTodo_shouldReturnTrue_whenOwner() {
        UUID todoId = UUID.randomUUID();
        TodoEntity todo = new TodoEntity();
        UserEntity user = new UserEntity();
        try (var mocked = mockStatic(com.dart.server.common.utils.AuthUtils.class)) {
            mocked.when(() -> com.dart.server.common.utils.AuthUtils.getUser(any(), any())).thenReturn(user);
            mocked.when(() -> com.dart.server.common.utils.AuthUtils.isOwner(any(), any())).thenReturn(true);
            mocked.when(com.dart.server.common.utils.AuthUtils::isAdmin).thenReturn(false);
            when(todoRepository.findByIdAndMarkedForDeletionFalse(todoId)).thenReturn(Optional.of(todo));
            when(todoRepository.save(any())).thenReturn(todo);
            boolean result = todoService.deleteTodo(todoId, "user");
            assertTrue(result);
        }
    }
}

