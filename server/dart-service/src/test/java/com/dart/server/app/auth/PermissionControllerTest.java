package com.dart.server.app.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PermissionControllerTest {
    private MockMvc mockMvc;
    @Mock
    PermissionService permissionService;
    @InjectMocks
    PermissionController permissionController;

    @BeforeEach
    void setUp() {
        try (AutoCloseable mocks = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(permissionController).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllPermissions_shouldReturnList() throws Exception {
        when(permissionService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/permissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void getPermissionById_shouldReturnPermission() throws Exception {
        UUID id = UUID.randomUUID();
        PermissionEntity entity = new PermissionEntity();
        entity.setId(id);
        entity.setName("PERM");
        when(permissionService.findById(id)).thenReturn(Optional.of(entity));
        mockMvc.perform(get("/api/permissions/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()));
    }

    @Test
    void getPermissionById_shouldReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(permissionService.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/permissions/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void createPermission_shouldReturnCreated() throws Exception {
        UUID id = UUID.randomUUID();
        PermissionEntity entity = new PermissionEntity();
        entity.setId(id);
        entity.setName("PERM");
        when(permissionService.save(any())).thenReturn(entity);
        mockMvc.perform(post("/api/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()));
    }

    @Test
    void updatePermission_shouldReturnUpdated() throws Exception {
        UUID id = UUID.randomUUID();
        PermissionEntity entity = new PermissionEntity();
        entity.setId(id);
        entity.setName("PERM");
        when(permissionService.findById(id)).thenReturn(Optional.of(entity));
        when(permissionService.save(any())).thenReturn(entity);
        mockMvc.perform(put("/api/permissions/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()));
    }

    @Test
    void updatePermission_shouldReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        PermissionEntity entity = new PermissionEntity();
        entity.setId(id);
        entity.setName("PERM");
        when(permissionService.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/permissions/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void deletePermission_shouldReturnDeleted() throws Exception {
        UUID id = UUID.randomUUID();
        PermissionEntity entity = new PermissionEntity();
        entity.setId(id);
        when(permissionService.findById(id)).thenReturn(Optional.of(entity));
        mockMvc.perform(delete("/api/permissions/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void deletePermission_shouldReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(permissionService.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/permissions/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }
}
