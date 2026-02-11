package com.dart.server.app.peddit.vote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VoteControllerDeleteTest {
    private MockMvc mockMvc;
    @Mock
    VoteService voteService;
    @InjectMocks
    VoteController voteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(voteController).build();
    }

    @Test
    void delete_shouldReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(voteService.getById(id)).thenReturn(null);
        mockMvc.perform(delete("/api/votes/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Vote not found"));
    }

    @Test
    void delete_shouldReturnAlreadyMarked() throws Exception {
        UUID id = UUID.randomUUID();
        VoteEntity entity = new VoteEntity();
        entity.setId(id);
        entity.setMarkedForDeletion(true);
        when(voteService.getById(id)).thenReturn(entity);
        mockMvc.perform(delete("/api/votes/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Vote already marked for deletion"));
    }

    @Test
    void delete_shouldReturnMarkedForDeletion() throws Exception {
        UUID id = UUID.randomUUID();
        VoteEntity entity = new VoteEntity();
        entity.setId(id);
        entity.setMarkedForDeletion(false);
        when(voteService.getById(id)).thenReturn(entity);
        when(voteService.save(entity)).thenReturn(entity);
        mockMvc.perform(delete("/api/votes/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Marked for deletion"))
                .andExpect(jsonPath("$.data").value(true));
    }
}

