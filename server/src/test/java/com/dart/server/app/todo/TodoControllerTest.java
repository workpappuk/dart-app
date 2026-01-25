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
        mockMvc.perform(get("/api/todos/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void getTodoById_shouldReturnTodo() throws Exception {
        TodoResponse todo = new TodoResponse();
        todo.setId(1L);
        when(todoService.getTodoById(1L)).thenReturn(Optional.of(todo));
        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    void getTodoById_shouldReturnNotFound() throws Exception {
        when(todoService.getTodoById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void createTodo_shouldReturnCreated() throws Exception {
        TodoResponse todo = new TodoResponse();
        todo.setId(1L);
        com.dart.server.app.todo.dto.TodoRequest req = new com.dart.server.app.todo.dto.TodoRequest();
        org.springframework.security.core.Authentication auth = org.mockito.Mockito.mock(org.springframework.security.core.Authentication.class);
        org.mockito.Mockito.when(auth.getName()).thenReturn("user");
        when(todoService.createTodo(any(), any())).thenReturn(todo);
        mockMvc.perform(post("/api/todos")
                        .principal(auth)
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    void updateTodo_shouldReturnUpdated() throws Exception {
        TodoResponse todo = new TodoResponse();
        todo.setId(1L);
        com.dart.server.app.todo.dto.TodoRequest req = new com.dart.server.app.todo.dto.TodoRequest();
        org.springframework.security.core.Authentication auth = org.mockito.Mockito.mock(org.springframework.security.core.Authentication.class);
        org.mockito.Mockito.when(auth.getName()).thenReturn("user");
        when(todoService.updateTodo(any(Long.class), any(), any())).thenReturn(java.util.Optional.of(todo));
        mockMvc.perform(put("/api/todos/1")
                        .principal(auth)
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    void updateTodo_shouldReturnNotFound() throws Exception {
        com.dart.server.app.todo.dto.TodoRequest req = new com.dart.server.app.todo.dto.TodoRequest();
        org.springframework.security.core.Authentication auth = org.mockito.Mockito.mock(org.springframework.security.core.Authentication.class);
        org.mockito.Mockito.when(auth.getName()).thenReturn("user");
        when(todoService.updateTodo(any(Long.class), any(), any())).thenReturn(java.util.Optional.empty());
        mockMvc.perform(put("/api/todos/1")
                        .principal(auth)
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void deleteTodo_shouldReturnDeleted() throws Exception {
        when(todoService.deleteTodo(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void deleteTodo_shouldReturnNotFound() throws Exception {
        when(todoService.deleteTodo(1L)).thenReturn(false);
        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }
}
