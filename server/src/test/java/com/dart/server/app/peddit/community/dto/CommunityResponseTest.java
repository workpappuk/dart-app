package com.dart.server.app.peddit.community.dto;

import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CommunityResponseTest {
    @Test
    void testSettersAndGetters() {
        CommunityResponse resp = new CommunityResponse();
        UUID id = UUID.randomUUID();
        resp.setId(id);
        resp.setName("name");
        resp.setDescription("desc");
        resp.setMarkedForDeletion(true);
        assertEquals(id, resp.getId());
        assertEquals("name", resp.getName());
        assertEquals("desc", resp.getDescription());
        assertTrue(resp.isMarkedForDeletion());
    }
    @Test
    void testDefaultValues() {
        CommunityResponse resp = new CommunityResponse();
        assertNull(resp.getId());
        assertNull(resp.getName());
        assertNull(resp.getDescription());
        assertFalse(resp.isMarkedForDeletion());
    }
}
