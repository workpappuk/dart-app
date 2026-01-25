package com.dart.server.app.auth.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    @Test
    void testToResponseNull() {
        assertNull(UserMapper.toResponse(null));
    }

    @Test
    void testToResponseValid() {
        var entity = new com.dart.server.app.auth.UserEntity();
        entity.setId(1L);
        entity.setUsername("user");
        entity.setRoles(null);
        var response = UserMapper.toResponse(entity);
        assertEquals(1L, response.getId());
        assertEquals("user", response.getUsername());
        assertNull(response.getRoles());
    }

    @Test
    void testToResponseWithRoles() {
        var entity = new com.dart.server.app.auth.UserEntity();
        entity.setId(2L);
        entity.setUsername("user2");
        var role = new com.dart.server.app.auth.RoleEntity();
        role.setId(10L);
        role.setName("ADMIN");
        role.setPermissions(new java.util.HashSet<>()); // Fix: initialize permissions
        java.util.Set<com.dart.server.app.auth.RoleEntity> roles = new java.util.HashSet<>();
        roles.add(role);
        entity.setRoles(roles);
        var response = UserMapper.toResponse(entity);
        assertEquals(2L, response.getId());
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
