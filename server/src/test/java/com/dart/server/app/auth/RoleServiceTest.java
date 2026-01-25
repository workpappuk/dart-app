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
class RoleServiceTest {
    @Mock
    PermissionService permissionService;
    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    RoleService roleService;

    @BeforeEach
    void setUp() {
        // No need for MockitoAnnotations.openMocks(this) with @ExtendWith(MockitoExtension.class)
    }

    @Test
    void assignPermission_shouldAssign() {
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        PermissionEntity perm = new PermissionEntity();
        perm.setId(2L);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(permissionService.findById(2L)).thenReturn(Optional.of(perm));
        when(roleRepository.save(any())).thenReturn(role);
        roleService.assignPermission(1L, 2L);
        verify(roleRepository).save(role);
    }

    @Test
    void removePermission_shouldRemove() {
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        PermissionEntity perm = new PermissionEntity();
        perm.setId(2L);
        role.setPermissions(new java.util.HashSet<>(Collections.singletonList(perm)));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(permissionService.findById(2L)).thenReturn(Optional.of(perm));
        when(roleRepository.save(any())).thenReturn(role);
        roleService.removePermission(1L, 2L);
        verify(roleRepository).save(role);
    }

    @Test
    void findByName_shouldReturnEntity() {
        RoleEntity entity = new RoleEntity();
        when(roleRepository.findByName("role")).thenReturn(java.util.Optional.of(entity));
        assertNotNull(roleService.findByName("role"));
    }

    @Test
    void findAll_shouldReturnList() {
        when(roleRepository.findAll()).thenReturn(Collections.emptyList());
        assertNotNull(roleService.findAll());
    }

    @Test
    void findById_shouldReturnOptional() {
        RoleEntity entity = new RoleEntity();
        entity.setId(1L);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(entity));
        assertTrue(roleService.findById(1L).isPresent());
    }

    @Test
    void findById_shouldReturnEmpty() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        assertTrue(roleService.findById(1L).isEmpty());
    }

    @Test
    void save_shouldReturnEntity() {
        RoleEntity entity = new RoleEntity();
        when(roleRepository.save(entity)).thenReturn(entity);
        assertNotNull(roleService.save(entity));
    }

    @Test
    void deleteById_shouldCallRepository() {
        doNothing().when(roleRepository).deleteById(1L);
        roleService.deleteById(1L);
        verify(roleRepository).deleteById(1L);
    }
}
