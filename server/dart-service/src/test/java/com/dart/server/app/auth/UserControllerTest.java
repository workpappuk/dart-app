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

class UserControllerTest {
    private MockMvc mockMvc;
    @Mock
    UserService userService;
    @Mock
    RoleService roleService;
    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp() {
        try (AutoCloseable mocks = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void assignRoleToUser_shouldReturnOk() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        when(userService.findById(userId)).thenReturn(Optional.of(new UserEntity()));
        when(roleService.findById(roleId)).thenReturn(Optional.of(new RoleEntity()));
        mockMvc.perform(post("/api/users/" + userId + "/roles/" + roleId))
                .andExpect(status().isOk());
    }

    @Test
    void assignRoleToUser_shouldReturnNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        when(userService.findById(userId)).thenReturn(Optional.empty());
        when(roleService.findById(roleId)).thenReturn(Optional.of(new RoleEntity()));
        mockMvc.perform(post("/api/users/" + userId + "/roles/" + roleId))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeRoleFromUser_shouldReturnOk() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        when(userService.findById(userId)).thenReturn(Optional.of(new UserEntity()));
        when(roleService.findById(roleId)).thenReturn(Optional.of(new RoleEntity()));
        mockMvc.perform(delete("/api/users/" + userId + "/roles/" + roleId))
                .andExpect(status().isOk());
    }

    @Test
    void removeRoleFromUser_shouldReturnNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        when(userService.findById(userId)).thenReturn(Optional.of(new UserEntity()));
        when(roleService.findById(roleId)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/users/" + userId + "/roles/" + roleId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUsers_shouldReturnList() throws Exception {
        when(userService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        UUID userId = UUID.randomUUID();
        UserEntity entity = new UserEntity();
        entity.setId(userId);
        entity.setUsername("user");
        when(userService.findById(userId)).thenReturn(Optional.of(entity));
        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(userId.toString()));
    }

    @Test
    void getUserById_shouldReturnNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        when(userService.findById(userId)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void createUser_shouldReturnCreated() throws Exception {
        UUID userId = UUID.randomUUID();
        UserEntity entity = new UserEntity();
        entity.setId(userId);
        entity.setUsername("user");
        when(userService.save(any())).thenReturn(entity);
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(userId.toString()));
    }

    @Test
    void updateUser_shouldReturnUpdated() throws Exception {
        UUID userId = UUID.randomUUID();
        UserEntity entity = new UserEntity();
        entity.setId(userId);
        entity.setUsername("user");
        when(userService.findById(userId)).thenReturn(Optional.of(entity));
        when(userService.save(any())).thenReturn(entity);
        mockMvc.perform(put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(userId.toString()));
    }

    @Test
    void updateUser_shouldReturnNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        UserEntity entity = new UserEntity();
        entity.setId(userId);
        entity.setUsername("user");
        when(userService.findById(userId)).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void deleteUser_shouldReturnNoContent() throws Exception {
        UUID userId = UUID.randomUUID();
        UserEntity entity = new UserEntity();
        entity.setId(userId);
        when(userService.findById(userId)).thenReturn(Optional.of(entity));
        mockMvc.perform(delete("/api/users/" + userId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_shouldReturnNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        when(userService.findById(userId)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/users/" + userId))
                .andExpect(status().isNotFound());
    }
}
