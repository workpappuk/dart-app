package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {
    @Test
    void testSettersAndGetters() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("test");
        user.setPassword("pass");
        assertEquals(1L, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals("pass", user.getPassword());
    }
}

