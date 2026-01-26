package com.dart.server.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {
    @Test
    void corsConfigurerBeanNotNull() {
        CorsConfig config = new CorsConfig();
        WebMvcConfigurer conf = config.corsConfigurer();
        assertNotNull(conf);
    }
}

