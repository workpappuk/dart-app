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
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(permissionController).build();
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
        PermissionEntity entity = new PermissionEntity();
        entity.setId(1L);
        entity.setName("PERM");
        when(permissionService.findById(1L)).thenReturn(Optional.of(entity));
        mockMvc.perform(get("/api/permissions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    void getPermissionById_shouldReturnNotFound() throws Exception {
        when(permissionService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/permissions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void createPermission_shouldReturnCreated() throws Exception {
        PermissionEntity entity = new PermissionEntity();
        entity.setId(1L);
        entity.setName("PERM");
        when(permissionService.save(any())).thenReturn(entity);
        mockMvc.perform(post("/api/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L));
    }
}
