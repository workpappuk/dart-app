package com.dart.server.app.peddit.vote;

import com.dart.server.app.peddit.vote.dto.VoteRequest;
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

class VoteControllerTest {
    private MockMvc mockMvc;
    @Mock
    VoteService voteService;
    @InjectMocks
    VoteController voteController;

    @BeforeEach
    void setUp() {
        try (AutoCloseable mocks = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(voteController).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAll_shouldReturnList() throws Exception {
        when(voteService.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/votes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void getById_shouldReturnVote() throws Exception {
        VoteEntity entity = new VoteEntity();
        UUID id = UUID.randomUUID();
        entity.setId(id);
        when(voteService.getById(any(UUID.class))).thenReturn(entity);
        mockMvc.perform(get("/api/votes/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()));
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        VoteEntity entity = new VoteEntity();
        UUID id = UUID.randomUUID();
        entity.setId(id);
        VoteRequest req = new VoteRequest();
        when(voteService.create(any())).thenReturn(entity);
        mockMvc.perform(post("/api/votes")
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()));
    }

    @Test
    void getById_shouldReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(voteService.getById(any(UUID.class))).thenReturn(null);
        mockMvc.perform(get("/api/votes/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void create_shouldReturnErrorForNullRequest() throws Exception {
        when(voteService.create(any())).thenReturn(null);
        VoteRequest req = new VoteRequest();
        mockMvc.perform(post("/api/votes")
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void create_shouldReturnErrorForInvalidRequest() throws Exception {
        mockMvc.perform(post("/api/votes")
                        .contentType("application/json")
                        .content("")) // empty body
                .andExpect(status().isBadRequest());
    }
}
