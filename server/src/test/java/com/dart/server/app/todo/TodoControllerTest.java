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
}
