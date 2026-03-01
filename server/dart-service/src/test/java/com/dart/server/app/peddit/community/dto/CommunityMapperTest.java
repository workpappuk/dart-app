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
        entity.setMarkedForDeletion(true);
        entity.setCreatedAt(java.time.Instant.now());
        entity.setUpdatedAt(java.time.Instant.now());
        // Mock UserEntity for createdBy and updatedBy
        com.dart.server.app.auth.UserEntity createdBy = new com.dart.server.app.auth.UserEntity();
        createdBy.setId(UUID.randomUUID());
        createdBy.setUsername("user1");
        com.dart.server.app.auth.UserEntity updatedBy = new com.dart.server.app.auth.UserEntity();
        updatedBy.setId(UUID.randomUUID());
        updatedBy.setUsername("user2");
        CommunityMapper.CommunityWithUser withUser = new CommunityMapper.CommunityWithUser(entity, createdBy, updatedBy);
        CommunityResponse resp = CommunityMapper.toResponse(withUser);
        assertEquals(id, resp.getId());
        assertEquals("name", resp.getName());
        assertEquals("desc", resp.getDescription());
        assertTrue(resp.isMarkedForDeletion());
        assertNotNull(resp.getCreatedByUserInfo());
        assertEquals("user1", resp.getCreatedByUserInfo().getUsername());
        assertNotNull(resp.getUpdatedByUserInfo());
        assertEquals("user2", resp.getUpdatedByUserInfo().getUsername());
    }

    @Test
    void testToEntityWithNullRequest() {
        CommunityEntity entity = CommunityMapper.toEntity(null);
        assertNull(entity.getName());
        assertNull(entity.getDescription());
    }

    @Test
    void testToResponseWithNullEntity() {
        CommunityEntity entity = new CommunityEntity();
        CommunityMapper.CommunityWithUser withUser = new CommunityMapper.CommunityWithUser(entity, null, null);
        CommunityResponse resp = CommunityMapper.toResponse(withUser);
        assertNull(resp.getId());
        assertNull(resp.getName());
        assertNull(resp.getDescription());
        assertNull(resp.getCreatedByUserInfo());
        assertNull(resp.getUpdatedByUserInfo());
        assertFalse(resp.isMarkedForDeletion());
    }

    @Test
    void testToResponseWithNullFields() {
        CommunityEntity entity = new CommunityEntity();
        entity.setId(null);
        entity.setName(null);
        entity.setDescription(null);
        entity.setMarkedForDeletion(false);
        CommunityMapper.CommunityWithUser withUser = new CommunityMapper.CommunityWithUser(entity, null, null);
        CommunityResponse resp = CommunityMapper.toResponse(withUser);
        assertNull(resp.getId());
        assertNull(resp.getName());
        assertNull(resp.getDescription());
        assertNull(resp.getCreatedByUserInfo());
        assertNull(resp.getUpdatedByUserInfo());
        assertFalse(resp.isMarkedForDeletion());
    }
}
