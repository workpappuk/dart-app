package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleEntityTest {
    @Test
    void testSettersAndGetters() {
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        role.setName("ADMIN");
        assertEquals(1L, role.getId());
        assertEquals("ADMIN", role.getName());
    }
}

