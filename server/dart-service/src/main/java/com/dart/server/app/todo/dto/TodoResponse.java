package com.dart.server.app.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Schema(description = "Response body for a todo item")
public class TodoResponse {
    @Schema(description = "Todo ID", example = "1")
    private UUID id;

    @Schema(description = "Todo description", example = "Buy groceries")
    private String description;

    @Schema(description = "Completion status", example = "false")
    private boolean completed;

    @Schema(description = "Creation timestamp", example = "2023-10-10T10:00:00")
    private Instant createdAt;

    @Schema(description = "Last update timestamp", example = "2023-10-10T10:00:00")
    private Instant updatedAt;

    @Schema(description = "User who created the todo", example = "john_doe")
    private String createdBy;

    @Schema(description = "User who last updated the todo", example = "john_doe")
    private String updatedBy;

    @Schema(description = "Soft deletion flag", example = "false")
    private boolean markedForDeletion;

}
