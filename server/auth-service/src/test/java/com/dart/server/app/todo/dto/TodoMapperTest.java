package com.dart.server.app.todo.dto;

import com.dart.server.app.todo.TodoEntity;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TodoMapperTest {
    @Test
    void testToResponse() {
        TodoEntity todo = new TodoEntity();
        UUID id = UUID.randomUUID();
        todo.setId(id);
        todo.setDescription("desc");
        todo.setCompleted(true);
        Instant now = Instant.now();
        todo.setCreatedAt(now);
        todo.setUpdatedAt(now);
        todo.setCreatedBy("user");
        todo.setUpdatedBy("user2");
        todo.setMarkedForDeletion(true);
        TodoResponse resp = TodoMapper.toResponse(todo);
        assertEquals(id, resp.getId());
        assertEquals("desc", resp.getDescription());
        assertTrue(resp.isCompleted());
        assertEquals(now, resp.getCreatedAt());
        assertEquals(now, resp.getUpdatedAt());
        assertEquals("user", resp.getCreatedBy());
        assertEquals("user2", resp.getUpdatedBy());
        assertTrue(resp.isMarkedForDeletion());
    }
    @Test
    void testToEntity() {
        TodoRequest req = new TodoRequest();
        req.setDescription("desc");
        req.setCompleted(true);
        TodoEntity todo = TodoMapper.toEntity(req);
        assertEquals("desc", todo.getDescription());
        assertTrue(todo.isCompleted());
    }

    @Test
    void testToResponseWithNullEntity() {
        TodoResponse resp = TodoMapper.toResponse(new TodoEntity());
        assertNull(resp.getId());
        assertNull(resp.getDescription());
        assertFalse(resp.isCompleted());
        assertNull(resp.getCreatedAt());
        assertNull(resp.getUpdatedAt());
        assertNull(resp.getCreatedBy());
        assertNull(resp.getUpdatedBy());
        assertFalse(resp.isMarkedForDeletion());
    }

    @Test
    void testToEntityWithNullRequest() {
        TodoEntity todo = TodoMapper.toEntity(null);
        assertNull(todo.getDescription());
        assertFalse(todo.isCompleted());
    }
}
