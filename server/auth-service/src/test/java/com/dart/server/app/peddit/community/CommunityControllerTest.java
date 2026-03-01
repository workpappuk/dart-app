package com.dart.server.app.peddit.community;

import com.dart.server.app.peddit.community.dto.CommunityRequest;
import com.dart.server.app.peddit.community.dto.CommunityResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CommunityControllerTest {
    private MockMvc mockMvc;
    @Mock
    CommunityService communityService;
    @InjectMocks
    CommunityController communityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(communityController).build();
    }

    // @Test
    // void getAll_shouldReturnList() throws Exception {
    //     when(communityService.getAll()).thenReturn(Collections.emptyList());
    //     mockMvc.perform(get("/api/communities"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.success").value(true));
    // }

    @Test
    void getById_shouldReturnCommunity() throws Exception {
        CommunityResponse resp = new CommunityResponse();
        UUID id = UUID.randomUUID();
        resp.setId(id);
        when(communityService.getById(any(UUID.class))).thenReturn(resp);
        mockMvc.perform(get("/api/communities/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()));
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        CommunityResponse resp = new CommunityResponse();
        UUID id = UUID.randomUUID();
        resp.setId(id);
        CommunityRequest req = new CommunityRequest();
        when(communityService.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/communities")
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()));
    }

    @Test
    void update_shouldReturnUpdated() throws Exception {
        CommunityResponse resp = new CommunityResponse();
        UUID id = UUID.randomUUID();
        resp.setId(id);
        CommunityRequest req = new CommunityRequest();
        when(communityService.update(any(UUID.class), any())).thenReturn(resp);
        mockMvc.perform(put("/api/communities/" + id)
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()));
    }

    @Test
    void delete_shouldReturnTrue() throws Exception {
        UUID id = UUID.randomUUID();
        CommunityEntity entity = new CommunityEntity();
        entity.setId(id);
        entity.setMarkedForDeletion(false);
        when(communityService.getByIdEntity(any(UUID.class))).thenReturn(entity);
        when(communityService.save(any())).thenReturn(entity);
        mockMvc.perform(delete("/api/communities/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Marked for deletion"));
    }

    @Test
    void delete_shouldReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(communityService.getByIdEntity(any(UUID.class))).thenReturn(null);
        mockMvc.perform(delete("/api/communities/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Community not found"));
    }

    @Test
    void delete_shouldReturnAlreadyMarked() throws Exception {
        UUID id = UUID.randomUUID();
        CommunityEntity entity = new CommunityEntity();
        entity.setId(id);
        entity.setMarkedForDeletion(true);
        when(communityService.getByIdEntity(any(UUID.class))).thenReturn(entity);
        mockMvc.perform(delete("/api/communities/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Community already marked for deletion"));
    }
}
