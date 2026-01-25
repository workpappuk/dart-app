package com.dart.server.app.auth.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    @Test
    void testToResponseNull() {
        assertNull(UserMapper.toResponse(null));
    }
    // Add more tests for mapping logic as needed
}
