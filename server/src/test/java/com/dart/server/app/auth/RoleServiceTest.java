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
        UUID roleId = UUID.randomUUID();
        role.setId(roleId);
        PermissionEntity perm = new PermissionEntity();
        UUID permId = UUID.randomUUID();
        perm.setId(permId);
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(permissionService.findById(permId)).thenReturn(Optional.of(perm));
        when(roleRepository.save(any())).thenReturn(role);
        roleService.assignPermission(roleId, permId);
        verify(roleRepository).save(role);
    }

    @Test
    void removePermission_shouldRemove() {
        RoleEntity role = new RoleEntity();
        UUID roleId = UUID.randomUUID();
        role.setId(roleId);
        PermissionEntity perm = new PermissionEntity();
        UUID permId = UUID.randomUUID();
        perm.setId(permId);
        role.setPermissions(new java.util.HashSet<>(Collections.singletonList(perm)));
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(permissionService.findById(permId)).thenReturn(Optional.of(perm));
        when(roleRepository.save(any())).thenReturn(role);
        roleService.removePermission(roleId, permId);
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
        UUID roleId = UUID.randomUUID();
        entity.setId(roleId);
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(entity));
        assertTrue(roleService.findById(roleId).isPresent());
    }

    @Test
    void findById_shouldReturnEmpty() {
        UUID roleId = UUID.randomUUID();
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());
        assertTrue(roleService.findById(roleId).isEmpty());
    }

    @Test
    void save_shouldReturnEntity() {
        RoleEntity entity = new RoleEntity();
        when(roleRepository.save(entity)).thenReturn(entity);
        assertNotNull(roleService.save(entity));
    }

    @Test
    void deleteById_shouldCallRepository() {
        UUID roleId = UUID.randomUUID();
        doNothing().when(roleRepository).deleteById(roleId);
        roleService.deleteById(roleId);
        verify(roleRepository).deleteById(roleId);
    }
}
