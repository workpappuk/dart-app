package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RoleServiceTest {
    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    RoleService roleService;

    @Test
    void contextLoads() {
        MockitoAnnotations.openMocks(this);
    }
}

