package com.dart.server.app.todo;

import com.dart.server.app.todo.dto.TodoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TodoControllerTest {
    private MockMvc mockMvc;
    @Mock
    TodoService todoService;
    @InjectMocks
    TodoController todoController;

    @SuppressWarnings("resource")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
    }

    @Test
    void searchTodos_shouldReturnPage() throws Exception {
        var page = new org.springframework.data.domain.PageImpl<TodoResponse>(Collections.emptyList());
        when(todoService.searchTodos(any(), any(Integer.class), any(Integer.class))).thenReturn(page);
        org.springframework.security.core.Authentication auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken("user", null);
        mockMvc.perform(get("/api/todos/search").principal(auth))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void getTodoById_shouldReturnTodo() throws Exception {
        TodoResponse todo = new TodoResponse();
        UUID todoId = UUID.randomUUID();
        todo.setId(todoId);
        when(todoService.getTodoById(any(UUID.class), any())).thenReturn(Optional.of(todo));
        org.springframework.security.core.Authentication auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken("user", null);
        mockMvc.perform(get("/api/todos/" + todoId).principal(auth))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(todoId.toString()));
    }

    @Test
    void getTodoById_shouldReturnNotFound() throws Exception {
        when(todoService.getTodoById(any(UUID.class), any())).thenReturn(Optional.empty());
        org.springframework.security.core.Authentication auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken("user", null);
        UUID todoId = UUID.randomUUID();
        mockMvc.perform(get("/api/todos/" + todoId).principal(auth))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void createTodo_shouldReturnCreated() throws Exception {
        TodoResponse todo = new TodoResponse();
        UUID todoId = UUID.randomUUID();
        todo.setId(todoId);
        com.dart.server.app.todo.dto.TodoRequest req = new com.dart.server.app.todo.dto.TodoRequest();
        org.springframework.security.core.Authentication auth = org.mockito.Mockito.mock(org.springframework.security.core.Authentication.class);
        org.mockito.Mockito.when(auth.getName()).thenReturn("user");
        when(todoService.createTodo(any())).thenReturn(todo);
        mockMvc.perform(post("/api/todos")
                        .principal(auth)
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(todoId.toString()));
    }

    @Test
    void updateTodo_shouldReturnUpdated() throws Exception {
        TodoResponse todo = new TodoResponse();
        UUID todoId = UUID.randomUUID();
        todo.setId(todoId);
        com.dart.server.app.todo.dto.TodoRequest req = new com.dart.server.app.todo.dto.TodoRequest();
        org.springframework.security.core.Authentication auth = org.mockito.Mockito.mock(org.springframework.security.core.Authentication.class);
        org.mockito.Mockito.when(auth.getName()).thenReturn("user");
        when(todoService.updateTodo(any(UUID.class), any(), any())).thenReturn(java.util.Optional.of(todo));
        mockMvc.perform(put("/api/todos/" + todoId)
                        .principal(auth)
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(todoId.toString()));
    }

    @Test
    void updateTodo_shouldReturnNotFound() throws Exception {
        com.dart.server.app.todo.dto.TodoRequest req = new com.dart.server.app.todo.dto.TodoRequest();
        org.springframework.security.core.Authentication auth = org.mockito.Mockito.mock(org.springframework.security.core.Authentication.class);
        org.mockito.Mockito.when(auth.getName()).thenReturn("user");
        UUID todoId = UUID.randomUUID();
        when(todoService.updateTodo(any(UUID.class), any(), any())).thenReturn(java.util.Optional.empty());
        mockMvc.perform(put("/api/todos/" + todoId)
                        .principal(auth)
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void deleteTodo_shouldReturnDeleted() throws Exception {
        UUID todoId = UUID.randomUUID();
        when(todoService.deleteTodo(any(UUID.class), any())).thenReturn(true);
        org.springframework.security.core.Authentication auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken("user", null);
        mockMvc.perform(delete("/api/todos/" + todoId).principal(auth))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void deleteTodo_shouldReturnNotFound() throws Exception {
        UUID todoId = UUID.randomUUID();
        when(todoService.deleteTodo(any(UUID.class), any())).thenReturn(false);
        org.springframework.security.core.Authentication auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken("user", null);
        mockMvc.perform(delete("/api/todos/" + todoId).principal(auth))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

}
