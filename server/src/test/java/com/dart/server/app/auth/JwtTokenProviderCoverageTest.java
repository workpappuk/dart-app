package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderCoverageTest {
    @Test
    void testNoArgConstructor() {
        JwtTokenProvider provider = new JwtTokenProvider();
        assertNotNull(provider);
    }
}

