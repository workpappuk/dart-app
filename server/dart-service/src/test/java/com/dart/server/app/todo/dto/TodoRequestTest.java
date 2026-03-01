package com.dart.server.app.todo.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoRequestTest {
    @Test
    void testSettersAndGetters() {
        TodoRequest req = new TodoRequest();
        req.setDescription("desc");
        req.setCompleted(true);
        assertEquals("desc", req.getDescription());
        assertTrue(req.isCompleted());
    }
    @Test
    void testDefaultValues() {
        TodoRequest req = new TodoRequest();
        assertNull(req.getDescription());
        assertFalse(req.isCompleted());
    }
}

