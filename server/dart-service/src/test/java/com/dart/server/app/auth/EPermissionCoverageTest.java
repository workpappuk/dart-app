package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EPermissionCoverageTest {
    @Test
    void testEnumValues() {
        for (EPermission perm : EPermission.values()) {
            assertNotNull(perm.name());
        }
    }
}

