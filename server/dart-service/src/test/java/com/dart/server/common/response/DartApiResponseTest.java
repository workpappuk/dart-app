package com.dart.server.common.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DartApiResponseTest {
    @Test
    void testAllArgsConstructorAndGetters() {
        DartApiResponse<String> resp = new DartApiResponse<>(true, "msg", "data");
        assertTrue(resp.isSuccess());
        assertEquals("msg", resp.getMessage());
        assertEquals("data", resp.getData());
    }
}

