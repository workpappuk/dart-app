package com.dart.server.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {
    @Test
    void customOpenAPINotNull() {
        SwaggerConfig config = new SwaggerConfig();
        assertNotNull(config.customOpenAPI());
    }
}

