package com.dart.server.app.peddit.post.dto;

import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PostRequestTest {
    @Test
    void testSettersAndGetters() {
        PostRequest req = new PostRequest();
        UUID cid = UUID.randomUUID();
        req.setTitle("title");
        req.setContent("content");
        req.setCommunityId(cid);
        assertEquals("title", req.getTitle());
        assertEquals("content", req.getContent());
        assertEquals(cid, req.getCommunityId());
    }
    @Test
    void testDefaultValues() {
        PostRequest req = new PostRequest();
        assertNull(req.getTitle());
        assertNull(req.getContent());
        assertNull(req.getCommunityId());
    }
}

