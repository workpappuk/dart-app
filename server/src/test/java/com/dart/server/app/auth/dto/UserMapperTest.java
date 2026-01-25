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
    void testToEntity() {
        UserRequest req = new UserRequest();
        req.setUsername("user");
        req.setPassword("pass");
        var entity = UserMapper.toEntity(req);
        assertEquals("user", entity.getUsername());
        assertEquals("pass", entity.getPassword());
    }
}
