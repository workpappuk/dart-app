package com.dart.server.app.peddit.comment.dto;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import com.dart.server.app.peddit.EEntityTargetType;

import static org.junit.jupiter.api.Assertions.*;

class CommentResponseTest {
    @Test
    void testSettersAndGetters() {
        CommentResponse resp = new CommentResponse();
        UUID id = UUID.randomUUID();
        UUID tid = UUID.randomUUID();
        resp.setId(id);
        resp.setContent("comment");
        resp.setTargetId(tid);
        resp.setTargetType(EEntityTargetType.POST);
        resp.setMarkedForDeletion(true);
        assertEquals(id, resp.getId());
        assertEquals("comment", resp.getContent());
        assertEquals(tid, resp.getTargetId());
        assertEquals(EEntityTargetType.POST, resp.getTargetType());
        assertTrue(resp.isMarkedForDeletion());
    }
    @Test
    void testDefaultValues() {
        CommentResponse resp = new CommentResponse();
        assertNull(resp.getId());
        assertNull(resp.getContent());
        assertNull(resp.getTargetId());
        assertNull(resp.getTargetType());
        assertFalse(resp.isMarkedForDeletion());
    }
}

