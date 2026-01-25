package com.dart.server.app.auth.dto;

import com.dart.server.app.auth.PermissionEntity;
import com.dart.server.app.auth.dto.PermissionMapper;
import com.dart.server.app.auth.dto.PermissionRequest;
import com.dart.server.app.auth.dto.PermissionResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermissionMapperTest {
    @Test
    void testToResponse() {
        PermissionEntity entity = new PermissionEntity();
        entity.setId(1L);
        entity.setName("PERM");
        PermissionResponse response = PermissionMapper.toResponse(entity);
        assertEquals(1L, response.getId());
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

