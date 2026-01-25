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
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setRoles(new java.util.HashSet<>());
        RoleEntity role = new RoleEntity();
        role.setId(2L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleService.findById(2L)).thenReturn(Optional.of(role));
        when(userRepository.save(any())).thenReturn(user);
        userService.assignRole(1L, 2L);
        verify(userRepository).save(user);
    }

    @Test
    void removeRole_shouldRemove() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        RoleEntity role = new RoleEntity();
        role.setId(2L);
        user.setRoles(new java.util.HashSet<>(Collections.singletonList(role)));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleService.findById(2L)).thenReturn(Optional.of(role));
        when(userRepository.save(any())).thenReturn(user);
        userService.removeRole(1L, 2L);
        verify(userRepository).save(user);
    }

    @Test
    void findAll_shouldReturnList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        assertNotNull(userService.findAll());
    }

    @Test
    void findById_shouldReturnOptional() {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(entity));
        assertTrue(userService.findById(1L).isPresent());
    }

    @Test
    void findById_shouldReturnEmpty() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertTrue(userService.findById(1L).isEmpty());
    }

    @Test
    void save_shouldReturnEntity() {
        UserEntity entity = new UserEntity();
        when(userRepository.save(entity)).thenReturn(entity);
        assertNotNull(userService.save(entity));
    }

    @Test
    void deleteById_shouldCallRepository() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteById(1L);
        verify(userRepository).deleteById(1L);
    }
}
