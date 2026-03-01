package com.dart.server.app.peddit.vote.dto;

import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VoteResponseTest {
    @Test
    void testSettersAndGetters() {
        VoteResponse resp = new VoteResponse();
        UUID id = UUID.randomUUID();
        UUID tid = UUID.randomUUID();
        UUID uid = UUID.randomUUID();
        resp.setId(id);
        resp.setTargetId(tid);
        resp.setTargetType("POST");
        resp.setUserId(uid);
        resp.setUpvote(true);
        assertEquals(id, resp.getId());
        assertEquals(tid, resp.getTargetId());
        assertEquals("POST", resp.getTargetType());
        assertEquals(uid, resp.getUserId());
        assertTrue(resp.isUpvote());
    }
    @Test
    void testDefaultValues() {
        VoteResponse resp = new VoteResponse();
        assertNull(resp.getId());
        assertNull(resp.getTargetId());
        assertNull(resp.getTargetType());
        assertNull(resp.getUserId());
        assertFalse(resp.isUpvote());
    }
}

