package com.dart.server.app.peddit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EEntityTargetTypeTest {
    @Test
    void testEnumValues() {
        assertEquals(EEntityTargetType.POST, EEntityTargetType.valueOf("POST"));
        assertEquals(EEntityTargetType.COMMUNITY, EEntityTargetType.valueOf("COMMUNITY"));
        assertEquals(EEntityTargetType.COMMENT, EEntityTargetType.valueOf("COMMENT"));
        assertEquals(3, EEntityTargetType.values().length);
    }
}
