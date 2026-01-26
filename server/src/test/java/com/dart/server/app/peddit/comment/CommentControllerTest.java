package com.dart.server.app.peddit.comment;

import com.dart.server.app.peddit.comment.dto.CommentRequest;
import com.dart.server.app.peddit.comment.dto.CommentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CommentControllerTest {
    private MockMvc mockMvc;
    @Mock
    CommentService commentService;
    @InjectMocks
    CommentController commentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void getAll_shouldReturnList() throws Exception {
        when(commentService.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void getById_shouldReturnComment() throws Exception {
        UUID id = UUID.randomUUID();
        CommentResponse resp = new CommentResponse();
        resp.setId(id);
        when(commentService.getById(id)).thenReturn(resp);
        mockMvc.perform(get("/api/comments/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()));
    }

    @Test
    void getById_shouldReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(commentService.getById(id)).thenReturn(null);
        mockMvc.perform(get("/api/comments/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        UUID id = UUID.randomUUID();
        CommentRequest req = new CommentRequest();
        CommentResponse resp = new CommentResponse();
        resp.setId(id);
        when(commentService.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()));
    }

    @Test
    void update_shouldReturnUpdated() throws Exception {
        UUID id = UUID.randomUUID();
        CommentRequest req = new CommentRequest();
        CommentResponse resp = new CommentResponse();
        resp.setId(id);
        when(commentService.update(id, req)).thenReturn(resp);
        mockMvc.perform(put("/api/comments/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()));
    }

    @Test
    void update_shouldReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        CommentRequest req = new CommentRequest();
        when(commentService.update(id, req)).thenReturn(null);
        mockMvc.perform(put("/api/comments/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void delete_shouldReturnMarkedForDeletion() throws Exception {
        UUID id = UUID.randomUUID();
        CommentEntity entity = new CommentEntity();
        entity.setId(id);
        entity.setMarkedForDeletion(false);
        when(commentService.getByIdEntity(id)).thenReturn(entity);
        when(commentService.save(any())).thenReturn(entity);
        mockMvc.perform(delete("/api/comments/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    void delete_shouldReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(commentService.getByIdEntity(id)).thenReturn(null);
        mockMvc.perform(delete("/api/comments/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void delete_shouldReturnAlreadyMarked() throws Exception {
        UUID id = UUID.randomUUID();
        CommentEntity entity = new CommentEntity();
        entity.setId(id);
        entity.setMarkedForDeletion(true);
        when(commentService.getByIdEntity(id)).thenReturn(entity);
        mockMvc.perform(delete("/api/comments/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }
}

