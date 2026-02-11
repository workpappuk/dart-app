package com.dart.server.app.todo.dto;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TodoResponseTest {
    @Test
    void testSettersAndGetters() {
        TodoResponse resp = new TodoResponse();
        UUID id = UUID.randomUUID();
        resp.setId(id);
        resp.setDescription("desc");
        resp.setCompleted(true);
        Instant now = Instant.now();
        resp.setCreatedAt(now);
        resp.setUpdatedAt(now);
        resp.setCreatedBy("user");
        resp.setUpdatedBy("user2");
        resp.setMarkedForDeletion(true);
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
    void testDefaultValues() {
        TodoResponse resp = new TodoResponse();
        assertNull(resp.getId());
        assertNull(resp.getDescription());
        assertFalse(resp.isCompleted());
        assertNull(resp.getCreatedAt());
        assertNull(resp.getUpdatedAt());
        assertNull(resp.getCreatedBy());
        assertNull(resp.getUpdatedBy());
        assertFalse(resp.isMarkedForDeletion());
    }
}

