package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ERoleCoverageTest {
    @Test
    void testEnumValues() {
        for (ERole role : ERole.values()) {
            assertNotNull(role.name());
        }
    }
}

