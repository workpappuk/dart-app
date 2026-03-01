package com.dart.server.app.peddit.post;

import com.dart.server.app.peddit.post.dto.PostRequest;
import com.dart.server.app.peddit.post.dto.PostResponse;
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

class PostControllerTest {
    private MockMvc mockMvc;
    @Mock
    PostService postService;
    @InjectMocks
    PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController)
                .setControllerAdvice(new com.dart.server.common.advices.GlobalExceptionHandler())
                .build();
    }

    // @Test
    // void getAll_shouldReturnList() throws Exception {
    //     when(postService.getAll()).thenReturn(Collections.emptyList());
    //     mockMvc.perform(get("/api/posts"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.success").value(true));
    // }

    @Test
    void getById_shouldReturnPost() throws Exception {
        PostResponse resp = new PostResponse();
        UUID id = UUID.randomUUID();
        resp.setId(id);
        when(postService.getById(any(UUID.class))).thenReturn(resp);
        mockMvc.perform(get("/api/posts/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()));
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        PostResponse resp = new PostResponse();
        UUID id = UUID.randomUUID();
        resp.setId(id);
        PostRequest req = new PostRequest();
        when(postService.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()));
    }

    @Test
    void update_shouldReturnUpdated() throws Exception {
        PostResponse resp = new PostResponse();
        UUID id = UUID.randomUUID();
        resp.setId(id);
        PostRequest req = new PostRequest();
        when(postService.update(any(UUID.class), any())).thenReturn(resp);
        mockMvc.perform(put("/api/posts/" + id)
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(id.toString()));
    }

    @Test
    void delete_shouldReturnTrue() throws Exception {
        UUID id = UUID.randomUUID();
        PostEntity entity = new PostEntity();
        entity.setId(id);
        entity.setMarkedForDeletion(false);
        when(postService.getByIdEntity(any(UUID.class))).thenReturn(entity);
        when(postService.save(any())).thenReturn(entity);
        mockMvc.perform(delete("/api/posts/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Marked for deletion"));
    }

    @Test
    void delete_shouldReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(postService.getByIdEntity(any(UUID.class))).thenReturn(null);
        mockMvc.perform(delete("/api/posts/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Post not found"));
    }

    @Test
    void delete_shouldReturnAlreadyMarked() throws Exception {
        UUID id = UUID.randomUUID();
        PostEntity entity = new PostEntity();
        entity.setId(id);
        entity.setMarkedForDeletion(true);
        when(postService.getByIdEntity(any(UUID.class))).thenReturn(entity);
        mockMvc.perform(delete("/api/posts/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Post already marked for deletion"));
    }

    @Test
    void getById_shouldReturnBadRequest_onInvalidUUID() throws Exception {
        mockMvc.perform(get("/api/posts/invalid-uuid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_shouldReturnBadRequest_onEmptyBody() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(put("/api/posts/" + id)
                        .contentType("application/json")
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_shouldReturnServerError_onServiceException() throws Exception {
        PostRequest req = new PostRequest();
        when(postService.create(any())).thenThrow(new RuntimeException("fail"));
        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void update_shouldReturnServerError_onServiceException() throws Exception {
        UUID id = UUID.randomUUID();
        PostRequest req = new PostRequest();
        when(postService.update(any(UUID.class), any())).thenThrow(new RuntimeException("fail"));
        mockMvc.perform(put("/api/posts/" + id)
                        .contentType("application/json")
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void delete_shouldReturnServerError_onServiceException() throws Exception {
        UUID id = UUID.randomUUID();
        when(postService.getByIdEntity(any(UUID.class))).thenThrow(new RuntimeException("fail"));
        mockMvc.perform(delete("/api/posts/" + id))
                .andExpect(status().is5xxServerError());
    }
}
