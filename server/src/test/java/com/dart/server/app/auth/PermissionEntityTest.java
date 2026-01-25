package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermissionEntityTest {
    @Test
    void testSettersAndGetters() {
        PermissionEntity perm = new PermissionEntity();
        perm.setId(1L);
        perm.setName("PERM");
        assertEquals(1L, perm.getId());
        assertEquals("PERM", perm.getName());
    }
}

