package com.dart.server.app.auth.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    @Test
    void testToResponseNull() {
        assertNull(UserMapper.toResponse(null));
    }

    @Test
    void testToResponseValid() {
        var entity = new com.dart.server.app.auth.UserEntity();
        entity.setId(UUID.randomUUID());
        entity.setUsername("user");
        entity.setRoles(null);
        var response = UserMapper.toResponse(entity);
        assertNotNull(response.getId());
        assertEquals("user", response.getUsername());
        assertNull(response.getRoles());
    }

    @Test
    void testToResponseWithRoles() {
        var entity = new com.dart.server.app.auth.UserEntity();
        entity.setId(UUID.randomUUID());
        entity.setUsername("user2");
        var role = new com.dart.server.app.auth.RoleEntity();
        role.setId(UUID.randomUUID());
        role.setName("ADMIN");
        role.setPermissions(new java.util.HashSet<>()); // Fix: initialize permissions
        java.util.Set<com.dart.server.app.auth.RoleEntity> roles = new java.util.HashSet<>();
        roles.add(role);
        entity.setRoles(roles);
        var response = UserMapper.toResponse(entity);
        assertNotNull(response.getId());
        assertEquals("user2", response.getUsername());
        assertNotNull(response.getRoles());
        assertEquals(1, response.getRoles().size());
    }

    @Test
    void testToEntity() {
        UserRequest req = new UserRequest();
        req.setUsername("user");
        req.setPassword("pass");
        var entity = UserMapper.toEntity(req);
        assertEquals("user", entity.getUsername());
        assertEquals("pass", entity.getPassword());
    }

    @Test
    void testToEntityWithNulls() {
        UserRequest req = new UserRequest();
        req.setUsername(null);
        req.setPassword(null);
        var entity = UserMapper.toEntity(req);
        assertNull(entity.getUsername());
        assertNull(entity.getPassword());
    }
}
