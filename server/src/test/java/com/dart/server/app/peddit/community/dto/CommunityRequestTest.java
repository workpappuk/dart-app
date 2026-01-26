package com.dart.server.app.peddit.community.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommunityRequestTest {
    @Test
    void testSettersAndGetters() {
        CommunityRequest req = new CommunityRequest();
        req.setName("name");
        req.setDescription("desc");
        assertEquals("name", req.getName());
        assertEquals("desc", req.getDescription());
    }
    @Test
    void testDefaultValues() {
        CommunityRequest req = new CommunityRequest();
        assertNull(req.getName());
        assertNull(req.getDescription());
    }
}
