package com.dart.server.app.todo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dart.server.app.todo.dto.TodoRequest;
import com.dart.server.app.todo.dto.TodoResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    public List<TodoResponse> getAllTodos() {
        return todoService.getAllTodos();
    }

    @Operation(summary = "Search todos", description = "Search todos by description with pagination.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Paginated list of todos returned successfully")
    })
    @GetMapping("/search")
    public Page<TodoResponse> searchTodos(
            @Parameter(description = "Search query") @RequestParam(defaultValue = "") String q,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        return todoService.searchTodos(q, page, size);
    }

    @Operation(summary = "Get todo by ID", description = "Returns a single todo by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Todo found and returned"),
        @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@Parameter(description = "ID of the todo") @PathVariable Long id) {
        return todoService.getTodoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new todo", description = "Creates a new todo item.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Todo created successfully")
    })
    @PostMapping
    public TodoResponse createTodo(@Parameter(description = "Todo request body") @RequestBody TodoRequest request) {
        return todoService.createTodo(request);
    }

    @Operation(summary = "Update a todo", description = "Updates an existing todo item.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Todo updated successfully"),
        @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @Parameter(description = "ID of the todo") @PathVariable Long id,
            @Parameter(description = "Updated todo request body") @RequestBody TodoRequest request) {
        return todoService.updateTodo(id, request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a todo", description = "Deletes a todo item by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Todo deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@Parameter(description = "ID of the todo") @PathVariable Long id) {
        if (!todoService.deleteTodo(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
