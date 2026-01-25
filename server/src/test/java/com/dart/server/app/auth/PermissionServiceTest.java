package com.dart.server.app.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionServiceTest {
    @Mock
    PermissionRepository permissionRepository;
    @InjectMocks
    PermissionService permissionService;

    @BeforeEach
    void setUp() {
        // No need for MockitoAnnotations.openMocks(this) with @ExtendWith(MockitoExtension.class)
    }

    @Test
    void contextLoads() {
    }

    @Test
    void findAll_shouldReturnList() {
        when(permissionRepository.findAll()).thenReturn(Collections.emptyList());
        assertNotNull(permissionService.findAll());
    }

    @Test
    void findById_shouldReturnOptional() {
        PermissionEntity entity = new PermissionEntity();
        entity.setId(1L);
        when(permissionRepository.findById(1L)).thenReturn(Optional.of(entity));
        assertTrue(permissionService.findById(1L).isPresent());
    }

    @Test
    void findById_shouldReturnEmpty() {
        when(permissionRepository.findById(1L)).thenReturn(Optional.empty());
        assertTrue(permissionService.findById(1L).isEmpty());
    }

    @Test
    void save_shouldReturnEntity() {
        PermissionEntity entity = new PermissionEntity();
        when(permissionRepository.save(entity)).thenReturn(entity);
        assertNotNull(permissionService.save(entity));
    }

    @Test
    void deleteById_shouldCallRepository() {
        doNothing().when(permissionRepository).deleteById(1L);
        permissionService.deleteById(1L);
        verify(permissionRepository).deleteById(1L);
    }

    @Test
    void findByName_shouldReturnEntity() {
        PermissionEntity entity = new PermissionEntity();
        when(permissionRepository.findByName("perm")).thenReturn(entity);
        assertNotNull(permissionService.findByName("perm"));
    }
}
