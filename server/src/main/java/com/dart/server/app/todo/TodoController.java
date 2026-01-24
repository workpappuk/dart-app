package com.dart.server.app.todo;

import com.dart.server.app.todo.dto.TodoRequest;
import com.dart.server.app.todo.dto.TodoResponse;
import com.dart.server.common.utils.DartApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @Operation(summary = "Get all todos", description = "Returns a list of all todos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of todos returned successfully")
    })
    @GetMapping
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
        public DartApiResponse<List<TodoResponse>> getAllTodos() {
                List<TodoResponse> todos = todoService.getAllTodos();
                return DartApiResponse.<List<TodoResponse>>builder()
                        .success(true)
                        .message("Todos fetched successfully")
                        .data(todos)
                        .build();
        }

    @Operation(summary = "Search todos", description = "Search todos by description with pagination.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paginated list of todos returned successfully")
    })
    @GetMapping("/search")
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
        public DartApiResponse<Page<TodoResponse>> searchTodos(
            @Parameter(description = "Search query") @RequestParam(defaultValue = "") String q,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        Page<TodoResponse> result = todoService.searchTodos(q, page, size);
        return DartApiResponse.<Page<TodoResponse>>builder()
                .success(true)
                .message("Todos search result fetched successfully")
                .data(result)
                .build();
    }

    @Operation(summary = "Get todo by ID", description = "Returns a single todo by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Todo found and returned"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @GetMapping("/{id}")
    public DartApiResponse<TodoResponse> getTodoById(@Parameter(description = "ID of the todo") @PathVariable Long id) {
        return todoService.getTodoById(id)
                .map(todo -> DartApiResponse.<TodoResponse>builder()
                        .success(true)
                        .message("Todo found")
                        .data(todo)
                        .build())
                .orElseGet(() -> DartApiResponse.<TodoResponse>builder()
                        .success(false)
                        .message("Todo not found")
                        .data(null)
                        .build());
    }

    @Operation(summary = "Create a new todo", description = "Creates a new todo item.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Todo created successfully")
    })
    @PostMapping
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
        public DartApiResponse<TodoResponse> createTodo(@Parameter(description = "Todo request body") @RequestBody TodoRequest request, Authentication authentication) {
        String username = authentication.getName();
        TodoResponse todo = todoService.createTodo(request, username);
        return DartApiResponse.<TodoResponse>builder()
                .success(true)
                .message("Todo created successfully")
                .data(todo)
                .build();
    }

    @Operation(summary = "Update a todo", description = "Updates an existing todo item.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Todo updated successfully"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public DartApiResponse<TodoResponse> updateTodo(
            @Parameter(description = "ID of the todo") @PathVariable Long id,
            @Parameter(description = "Updated todo request body") @RequestBody TodoRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        return todoService.updateTodo(id, request, username)
                .map(todo -> DartApiResponse.<TodoResponse>builder()
                        .success(true)
                        .message("Todo updated successfully")
                        .data(todo)
                        .build())
                .orElseGet(() -> DartApiResponse.<TodoResponse>builder()
                        .success(false)
                        .message("Todo not found")
                        .data(null)
                        .build());
    }

    @Operation(summary = "Delete a todo", description = "Deletes a todo item by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Todo deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public DartApiResponse<Void> deleteTodo(@Parameter(description = "ID of the todo") @PathVariable Long id) {
        boolean deleted = todoService.deleteTodo(id);
        if (!deleted) {
            return DartApiResponse.<Void>builder()
                    .success(false)
                    .message("Todo not found")
                    .data(null)
                    .build();
        }
        return DartApiResponse.<Void>builder()
                .success(true)
                .message("Todo deleted successfully")
                .data(null)
                .build();
    }
}
