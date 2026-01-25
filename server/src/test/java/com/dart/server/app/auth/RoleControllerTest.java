package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoleControllerTest {
    @Mock
    RoleService roleService;
    @InjectMocks
    RoleController roleController;

    @Test
    void contextLoads() throws Exception {
        try (var mocks = MockitoAnnotations.openMocks(this)) {
            assertNotNull(roleService);
            assertNotNull(roleController);
        }
    }
}
