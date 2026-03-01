package com.dart.server.app.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request body for creating or updating a todo item")
public class TodoRequest {
    @Schema(description = "Todo description", example = "Buy groceries")
    private String description;

    @Schema(description = "Completion status", example = "false")
    private boolean completed;
}
