package com.dart.server.app.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response body for a todo item")
public class TodoResponse {
    @Schema(description = "Todo ID", example = "1")
    private Long id;

    @Schema(description = "Todo description", example = "Buy groceries")
    private String description;

    @Schema(description = "Completion status", example = "false")
    private boolean completed;

    @Schema(description = "Creation timestamp", example = "2023-10-10T10:00:00")
    private java.time.LocalDateTime createdAt;

    @Schema(description = "Last update timestamp", example = "2023-10-10T10:00:00")
    private java.time.LocalDateTime updatedAt;

    @Schema(description = "User who created the todo", example = "john_doe")
    private String createdBy;

    @Schema(description = "User who last updated the todo", example = "john_doe")
    private String updatedBy;

    // createdAt and updatedAt should be set at the entity level (in TodoEntity) using @PrePersist and @PreUpdate methods.
    // createdBy and updatedBy should be set at the entity level using an EntityListener (e.g., TodoAuditListener) that pulls the username from the security context.
    // This DTO is correct for mapping these values from the entity for API responses.
}
