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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    RoleService roleService;
    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        // No need for MockitoAnnotations.openMocks(this) with @ExtendWith(MockitoExtension.class)
    }

    @Test
    void contextLoads() {
        // No-op
    }

    @Test
    void existsByUsername_shouldReturnTrue() {
        when(userRepository.existsByUsername("user")).thenReturn(true);
        assertTrue(userService.existsByUsername("user"));
    }

    @Test
    void findByUsername_shouldReturnEntity() {
        UserEntity entity = new UserEntity();
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(entity));
        assertNotNull(userService.findByUsername("user"));
    }

    @Test
    void assignRole_shouldAssign() {
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setRoles(new java.util.HashSet<>());
        RoleEntity role = new RoleEntity();
        role.setId(roleId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleService.findById(roleId)).thenReturn(Optional.of(role));
        when(userRepository.save(any())).thenReturn(user);
        userService.assignRole(userId, roleId);
        verify(userRepository).save(user);
    }

    @Test
    void removeRole_shouldRemove() {
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        UserEntity user = new UserEntity();
        user.setId(userId);
        RoleEntity role = new RoleEntity();
        role.setId(roleId);
        user.setRoles(new java.util.HashSet<>(Collections.singletonList(role)));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleService.findById(roleId)).thenReturn(Optional.of(role));
        when(userRepository.save(any())).thenReturn(user);
        userService.removeRole(userId, roleId);
        verify(userRepository).save(user);
    }

    @Test
    void findAll_shouldReturnList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        assertNotNull(userService.findAll());
    }

    @Test
    void findById_shouldReturnOptional() {
        UUID userId = UUID.randomUUID();
        UserEntity entity = new UserEntity();
        entity.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(entity));
        assertTrue(userService.findById(userId).isPresent());
    }

    @Test
    void findById_shouldReturnEmpty() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertTrue(userService.findById(userId).isEmpty());
    }

    @Test
    void save_shouldReturnEntity() {
        UserEntity entity = new UserEntity();
        when(userRepository.save(entity)).thenReturn(entity);
        assertNotNull(userService.save(entity));
    }

    @Test
    void deleteById_shouldCallRepository() {
        UUID userId = UUID.randomUUID();
        doNothing().when(userRepository).deleteById(userId);
        userService.deleteById(userId);
        verify(userRepository).deleteById(userId);
    }
}
