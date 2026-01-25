package com.dart.server.common.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageResponseTest {
    @Test
    void testAllArgsConstructorAndGetters() {
        PageResponse<String> resp = new PageResponse<>(List.of("a"), 1, 2, 3L, 4);
        assertEquals(List.of("a"), resp.getContent());
        assertEquals(1, resp.getPage());
        assertEquals(2, resp.getSize());
        assertEquals(3L, resp.getTotalElements());
        assertEquals(4, resp.getTotalPages());
    }
}

