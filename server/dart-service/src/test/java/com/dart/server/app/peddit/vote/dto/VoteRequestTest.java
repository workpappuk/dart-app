package com.dart.server.app.peddit.vote.dto;

import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VoteRequestTest {
    @Test
    void testSettersAndGetters() {
        VoteRequest req = new VoteRequest();
        UUID tid = UUID.randomUUID();
        UUID uid = UUID.randomUUID();
        req.setTargetId(tid);
        req.setTargetType("POST");
        req.setUserId(uid);
        req.setUpvote(true);
        assertEquals(tid, req.getTargetId());
        assertEquals("POST", req.getTargetType());
        assertEquals(uid, req.getUserId());
        assertTrue(req.isUpvote());
    }
    @Test
    void testDefaultValues() {
        VoteRequest req = new VoteRequest();
        assertNull(req.getTargetId());
        assertNull(req.getTargetType());
        assertNull(req.getUserId());
        assertFalse(req.isUpvote());
    }
}

