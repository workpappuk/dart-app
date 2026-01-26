package com.dart.server.app.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

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
        UUID id = UUID.randomUUID();
        PermissionEntity entity = new PermissionEntity();
        entity.setId(id);
        when(permissionRepository.findById(id)).thenReturn(Optional.of(entity));
        assertTrue(permissionService.findById(id).isPresent());
    }

    @Test
    void findById_shouldReturnEmpty() {
        UUID id = UUID.randomUUID();
        when(permissionRepository.findById(id)).thenReturn(Optional.empty());
        assertTrue(permissionService.findById(id).isEmpty());
    }

    @Test
    void save_shouldReturnEntity() {
        PermissionEntity entity = new PermissionEntity();
        when(permissionRepository.save(entity)).thenReturn(entity);
        assertNotNull(permissionService.save(entity));
    }

    @Test
    void deleteById_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        doNothing().when(permissionRepository).deleteById(id);
        permissionService.deleteById(id);
        verify(permissionRepository).deleteById(id);
    }

    @Test
    void findByName_shouldReturnEntity() {
        PermissionEntity entity = new PermissionEntity();
        when(permissionRepository.findByName("perm")).thenReturn(entity);
        assertNotNull(permissionService.findByName("perm"));
    }
}
