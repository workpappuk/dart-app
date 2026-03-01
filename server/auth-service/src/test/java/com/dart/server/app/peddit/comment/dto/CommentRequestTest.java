package com.dart.server.app.peddit.comment.dto;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import com.dart.server.app.peddit.EEntityTargetType;

import static org.junit.jupiter.api.Assertions.*;

class CommentRequestTest {
    @Test
    void testSettersAndGetters() {
        CommentRequest req = new CommentRequest();
        req.setContent("comment");
        UUID tid = UUID.randomUUID();
        req.setTargetId(tid);
        req.setTargetType(EEntityTargetType.POST);
        assertEquals("comment", req.getContent());
        assertEquals(tid, req.getTargetId());
        assertEquals(EEntityTargetType.POST, req.getTargetType());
    }
    @Test
    void testDefaultValues() {
        CommentRequest req = new CommentRequest();
        assertNull(req.getContent());
        assertNull(req.getTargetId());
        assertNull(req.getTargetType());
    }
}

