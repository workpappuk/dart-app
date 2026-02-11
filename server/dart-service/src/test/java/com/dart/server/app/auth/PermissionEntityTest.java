package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PermissionEntityTest {
    @Test
    void testSettersAndGetters() {
        PermissionEntity perm = new PermissionEntity();
        UUID permId = UUID.randomUUID();
        perm.setId(permId);
        perm.setName("PERM");
        assertEquals(permId, perm.getId());
        assertEquals("PERM", perm.getName());
    }
}
