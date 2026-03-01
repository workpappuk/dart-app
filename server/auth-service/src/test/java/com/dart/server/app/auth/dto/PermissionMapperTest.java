package com.dart.server.app.auth.dto;

import com.auth.server.PermissionEntity;
import com.auth.server.dto.PermissionMapper;
import com.auth.server.dto.PermissionRequest;
import com.auth.server.dto.PermissionResponse;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PermissionMapperTest {
    @Test
    void testToResponse() {
        PermissionEntity entity = new PermissionEntity();
        entity.setId(UUID.randomUUID());
        entity.setName("PERM");
        PermissionResponse response = PermissionMapper.toResponse(entity);
        assertNotNull(response.getId());
        assertEquals("PERM", response.getName());
    }

    @Test
    void testToEntity() {
        PermissionRequest req = new PermissionRequest();
        req.setName("PERM");
        PermissionEntity entity = PermissionMapper.toEntity(req);
        assertEquals("PERM", entity.getName());
    }
}
