package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoleEntityTest {
    @Test
    void testSettersAndGetters() {
        RoleEntity role = new RoleEntity();
        UUID roleId = UUID.randomUUID();
        role.setId(roleId);
        role.setName("ADMIN");
        assertEquals(roleId, role.getId());
        assertEquals("ADMIN", role.getName());
    }
}
