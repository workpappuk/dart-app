package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {
    @Test
    void testSettersAndGetters() {
        UserEntity user = new UserEntity();
        UUID userId = UUID.randomUUID();
        user.setId(userId);
        user.setUsername("test");
        user.setPassword("pass");
        assertEquals(userId, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals("pass", user.getPassword());
    }
}
