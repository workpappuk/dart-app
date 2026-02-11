package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtBlacklistServiceCoverageTest {
    @Test
    void testNoArgConstructor() {
        JwtBlacklistService service = new JwtBlacklistService();
        assertNotNull(service);
    }
}

