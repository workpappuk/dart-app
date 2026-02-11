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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
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
        UUID roleId = UUID.randomUUID();
        UUID permId = UUID.randomUUID();
        RoleEntity role = new RoleEntity();
        role.setId(roleId);
        PermissionEntity perm = new PermissionEntity();
        perm.setId(permId);
        when(roleService.findById(roleId)).thenReturn(Optional.of(role));
        when(permissionService.findById(permId)).thenReturn(Optional.of(perm));
        mockMvc.perform(post("/api/roles/" + roleId + "/permissions/" + permId))
                .andExpect(status().isOk());
    }

    @Test
    void assignPermissionToRole_shouldReturnNotFound() throws Exception {
        UUID roleId = UUID.randomUUID();
        UUID permId = UUID.randomUUID();
        when(roleService.findById(roleId)).thenReturn(Optional.empty());
        when(permissionService.findById(permId)).thenReturn(Optional.of(new PermissionEntity()));
        mockMvc.perform(post("/api/roles/" + roleId + "/permissions/" + permId))
                .andExpect(status().isNotFound());
    }

    @Test
    void removePermissionFromRole_shouldReturnOk() throws Exception {
        UUID roleId = UUID.randomUUID();
        UUID permId = UUID.randomUUID();
        when(roleService.findById(roleId)).thenReturn(Optional.of(new RoleEntity()));
        when(permissionService.findById(permId)).thenReturn(Optional.of(new PermissionEntity()));
        mockMvc.perform(delete("/api/roles/" + roleId + "/permissions/" + permId))
                .andExpect(status().isOk());
    }

    @Test
    void removePermissionFromRole_shouldReturnNotFound() throws Exception {
        UUID roleId = UUID.randomUUID();
        UUID permId = UUID.randomUUID();
        when(roleService.findById(roleId)).thenReturn(Optional.of(new RoleEntity()));
        when(permissionService.findById(permId)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/roles/" + roleId + "/permissions/" + permId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllRoles_shouldReturnList() throws Exception {
        when(roleService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void getRoleById_shouldReturnRole() throws Exception {
        UUID roleId = UUID.randomUUID();
        RoleEntity entity = new RoleEntity();
        entity.setId(roleId);
        entity.setName("ROLE");
        when(roleService.findById(roleId)).thenReturn(Optional.of(entity));
        mockMvc.perform(get("/api/roles/" + roleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(roleId.toString()));
    }

    @Test
    void getRoleById_shouldReturnNotFound() throws Exception {
        UUID roleId = UUID.randomUUID();
        when(roleService.findById(roleId)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/roles/" + roleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void createRole_shouldReturnCreated() throws Exception {
        UUID roleId = UUID.randomUUID();
        RoleEntity entity = new RoleEntity();
        entity.setId(roleId);
        entity.setName("ROLE");
        when(roleService.save(any())).thenReturn(entity);
        mockMvc.perform(post("/api/roles")
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(roleId.toString()));
    }

    @Test
    void updateRole_shouldReturnUpdated() throws Exception {
        UUID roleId = UUID.randomUUID();
        RoleEntity entity = new RoleEntity();
        entity.setId(roleId);
        entity.setName("ROLE");
        when(roleService.findById(roleId)).thenReturn(Optional.of(entity));
        when(roleService.save(any())).thenReturn(entity);
        mockMvc.perform(put("/api/roles/" + roleId)
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(roleId.toString()));
    }

    @Test
    void updateRole_shouldReturnNotFound() throws Exception {
        UUID roleId = UUID.randomUUID();
        RoleEntity entity = new RoleEntity();
        entity.setId(roleId);
        entity.setName("ROLE");
        when(roleService.findById(roleId)).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/roles/" + roleId)
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void deleteRole_shouldReturnDeleted() throws Exception {
        UUID roleId = UUID.randomUUID();
        RoleEntity entity = new RoleEntity();
        entity.setId(roleId);
        when(roleService.findById(roleId)).thenReturn(Optional.of(entity));
        mockMvc.perform(delete("/api/roles/" + roleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void deleteRole_shouldReturnNotFound() throws Exception {
        UUID roleId = UUID.randomUUID();
        when(roleService.findById(roleId)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/roles/" + roleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }
}
