package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PermissionServiceTest {
    @Mock
    PermissionRepository permissionRepository;
    @InjectMocks
    PermissionService permissionService;

    @Test
    void contextLoads() {
        MockitoAnnotations.openMocks(this);
    }
}

