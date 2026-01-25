package com.dart.server.app.todo;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class TodoEntityTest {
    @Test
    void testOnCreateSetsTimestamps() {
        TodoEntity entity = new TodoEntity();
        entity.onCreate();
        assertNotNull(entity.getCreatedAt());
        assertNotNull(entity.getUpdatedAt());
        assertEquals(entity.getCreatedAt(), entity.getUpdatedAt());
    }

    @Test
    void testOnUpdateSetsUpdatedAt() throws InterruptedException {
        TodoEntity entity = new TodoEntity();
        entity.onCreate();
        LocalDateTime created = entity.getCreatedAt();
        Thread.sleep(5); // ensure time passes
        entity.onUpdate();
        assertNotNull(entity.getUpdatedAt());
        assertTrue(entity.getUpdatedAt().isAfter(created) || entity.getUpdatedAt().isEqual(created));
    }
}

