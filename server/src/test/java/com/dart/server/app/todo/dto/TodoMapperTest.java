package com.dart.server.app.todo.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoMapperTest {
    @Test
    void testToResponseWithValidEntity() {
        // Assuming TodoMapper.toResponse returns non-null for valid input
        var entity = new com.dart.server.app.todo.TodoEntity();
        entity.setId(1L);
        entity.setDescription("desc");
        assertNotNull(TodoMapper.toResponse(entity));
    }
    // Add more tests for mapping logic as needed
}
