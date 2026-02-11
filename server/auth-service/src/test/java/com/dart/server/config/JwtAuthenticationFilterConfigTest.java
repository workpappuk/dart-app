package com.dart.server.config;

import com.auth.server.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationFilterConfigTest {
    @Test
    void jwtAuthenticationFilterBeanNotNull() {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        assertNotNull(filter);
    }
}

