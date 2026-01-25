package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PermissionControllerTest {
    @Mock
    PermissionService permissionService;
    @InjectMocks
    PermissionController permissionController;
    @Test
    void contextLoads() {
        MockitoAnnotations.openMocks(this);
        assertNotNull(permissionService);
        assertNotNull(permissionController);
    }
}
