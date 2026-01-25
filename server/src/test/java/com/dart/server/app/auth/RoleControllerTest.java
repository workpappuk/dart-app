package com.dart.server.app.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RoleControllerTest {
    private MockMvc mockMvc;
    @Mock
    RoleService roleService;
    @Mock
    PermissionService permissionService;
    @InjectMocks
    RoleController roleController;

    @SuppressWarnings("resource")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    void assignPermissionToRole_shouldReturnOk() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.of(new RoleEntity()));
        when(permissionService.findById(2L)).thenReturn(Optional.of(new PermissionEntity()));
        mockMvc.perform(post("/api/roles/1/permissions/2"))
                .andExpect(status().isOk());
    }

    @Test
    void assignPermissionToRole_shouldReturnNotFound() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.empty());
        when(permissionService.findById(2L)).thenReturn(Optional.of(new PermissionEntity()));
        mockMvc.perform(post("/api/roles/1/permissions/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void removePermissionFromRole_shouldReturnOk() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.of(new RoleEntity()));
        when(permissionService.findById(2L)).thenReturn(Optional.of(new PermissionEntity()));
        mockMvc.perform(delete("/api/roles/1/permissions/2"))
                .andExpect(status().isOk());
    }

    @Test
    void removePermissionFromRole_shouldReturnNotFound() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.of(new RoleEntity()));
        when(permissionService.findById(2L)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/roles/1/permissions/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllRoles_shouldReturnList() throws Exception {
        when(roleService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
