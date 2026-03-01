package com.dart.server.app.peddit.post.dto;

import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PostResponseTest {
    @Test
    void testSettersAndGetters() {
        PostResponse resp = new PostResponse();
        UUID id = UUID.randomUUID();
        UUID cid = UUID.randomUUID();
        UUID aid = UUID.randomUUID();
        resp.setId(id);
        resp.setTitle("title");
        resp.setContent("content");
        resp.setCommunityId(cid);
        resp.setAuthorId(aid);
        resp.setMarkedForDeletion(true);
        assertEquals(id, resp.getId());
        assertEquals("title", resp.getTitle());
        assertEquals("content", resp.getContent());
        assertEquals(cid, resp.getCommunityId());
        assertEquals(aid, resp.getAuthorId());
        assertTrue(resp.isMarkedForDeletion());
    }
    @Test
    void testDefaultValues() {
        PostResponse resp = new PostResponse();
        assertNull(resp.getId());
        assertNull(resp.getTitle());
        assertNull(resp.getContent());
        assertNull(resp.getCommunityId());
        assertNull(resp.getAuthorId());
        assertFalse(resp.isMarkedForDeletion());
    }
}

