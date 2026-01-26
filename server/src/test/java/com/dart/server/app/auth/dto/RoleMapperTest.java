package com.dart.server.app.auth.dto;

import com.dart.server.app.auth.RoleEntity;
import com.dart.server.app.auth.dto.RoleMapper;
import com.dart.server.app.auth.dto.RoleRequest;
import com.dart.server.app.auth.dto.RoleResponse;
import com.dart.server.app.auth.PermissionEntity;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoleMapperTest {
    @Test
    void testToResponse() {
        RoleEntity entity = new RoleEntity();
        UUID roleId = UUID.randomUUID();
        entity.setId(roleId);
        entity.setName("ADMIN");
        PermissionEntity perm = new PermissionEntity();
        UUID permId = UUID.randomUUID();
        perm.setId(permId);
        perm.setName("PERM");
        entity.setPermissions(Set.of(perm));
        RoleResponse response = RoleMapper.toResponse(entity);
        assertEquals(roleId, response.getId());
        assertEquals("ADMIN", response.getName());
        assertEquals(1, response.getPermissions().size());
    }

    @Test
    void testToEntity() {
        RoleRequest req = new RoleRequest();
        req.setName("USER");
        RoleEntity entity = RoleMapper.toEntity(req);
        assertEquals("USER", entity.getName());
    }
}
