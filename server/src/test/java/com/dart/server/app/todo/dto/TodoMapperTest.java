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

    @Test
    void testToResponseNullInput() {
        assertThrows(NullPointerException.class, () -> TodoMapper.toResponse(null));
    }

    @Test
    void testToResponseWithNullCreatedBy() {
        var entity = new com.dart.server.app.todo.TodoEntity();
        entity.setId(2L);
        entity.setDescription("desc2");
        entity.setCompleted(true);
        // createdBy is null
        var response = TodoMapper.toResponse(entity);
        assertEquals("null", response.getCreatedBy());
        assertEquals("null", response.getUpdatedBy());
    }

    @Test
    void testToEntity() {
        TodoRequest req = new TodoRequest();
        req.setDescription("desc");
        req.setCompleted(true);
        var entity = TodoMapper.toEntity(req);
        assertEquals("desc", entity.getDescription());
        assertTrue(entity.isCompleted());
    }
    // Add more tests for mapping logic as needed
}
