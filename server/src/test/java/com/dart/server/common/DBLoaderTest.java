package com.dart.server.common;

import com.dart.server.app.auth.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DBLoaderTest {
    @Mock
    RoleService roleService;
    @Mock
    PermissionService permissionService;
    @Mock
    UserService userService;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    DBLoader dbLoader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testWarmDB() {
        // Mock permissionService
        when(permissionService.findByName(any())).thenReturn(null);
        when(permissionService.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        // Mock roleService
        when(roleService.findByName(any())).thenAnswer(invocation -> {
            String name = invocation.getArgument(0);
            RoleEntity role = new RoleEntity();
            role.setId(1L);
            role.setName(name);
            return role;
        });
        when(roleService.save(any())).thenAnswer(invocation -> {
            RoleEntity role = invocation.getArgument(0);
            role.setId(1L);
            return role;
        });
        // Mock userService
        when(userService.existsByUsername(any())).thenReturn(false);
        when(userService.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        // Mock passwordEncoder
        when(passwordEncoder.encode(any())).thenReturn("encoded");

        dbLoader.warmDB();
        verify(permissionService, atLeastOnce()).save(any());
        verify(roleService, atLeastOnce()).save(any());
        verify(userService, atLeastOnce()).save(any());
    }
}
