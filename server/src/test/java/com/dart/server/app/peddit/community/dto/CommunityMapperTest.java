package com.dart.server.app.peddit.community.dto;

import com.dart.server.app.peddit.community.CommunityEntity;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CommunityMapperTest {
    @Test
    void testToEntity() {
        CommunityRequest req = new CommunityRequest();
        req.setName("name");
        req.setDescription("desc");
        CommunityEntity entity = CommunityMapper.toEntity(req);
        assertEquals("name", entity.getName());
        assertEquals("desc", entity.getDescription());
    }
    @Test
    void testToResponse() {
        CommunityEntity entity = new CommunityEntity();
        UUID id = UUID.randomUUID();
        entity.setId(id);
        entity.setName("name");
        entity.setDescription("desc");
        entity.setCreatedBy("user");
        entity.setMarkedForDeletion(true);
        CommunityResponse resp = CommunityMapper.toResponse(entity);
        assertEquals(id, resp.getId());
        assertEquals("name", resp.getName());
        assertEquals("desc", resp.getDescription());
        assertEquals("user", resp.getCreatedBy());
        assertTrue(resp.isMarkedForDeletion());
    }

    @Test
    void testToEntityWithNullRequest() {
        CommunityEntity entity = CommunityMapper.toEntity(null);
        assertNull(entity.getName());
        assertNull(entity.getDescription());
    }

    @Test
    void testToResponseWithNullEntity() {
        CommunityResponse resp = CommunityMapper.toResponse(new CommunityEntity());
        assertNull(resp.getId());
        assertNull(resp.getName());
        assertNull(resp.getDescription());
        assertNull(resp.getCreatedBy());
        assertFalse(resp.isMarkedForDeletion());
    }
}
