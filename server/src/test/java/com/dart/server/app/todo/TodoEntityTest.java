package com.dart.server.app.todo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoEntityTest {
    @Test
    void testSettersAndGetters() {
        TodoEntity todo = new TodoEntity();
        todo.setId(1L);
        todo.setDescription("desc");
        assertEquals(1L, todo.getId());
        assertEquals("desc", todo.getDescription());
    }
}

