package com.dart.server.config;

import com.dart.server.app.auth.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationFilterConfigTest {
    @Test
    void jwtAuthenticationFilterBeanNotNull() {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        assertNotNull(filter);
    }
}

