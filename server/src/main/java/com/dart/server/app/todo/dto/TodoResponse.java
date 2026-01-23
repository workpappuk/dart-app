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
}
