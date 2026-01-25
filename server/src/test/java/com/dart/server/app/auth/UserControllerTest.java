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
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void assignRoleToUser_shouldReturnOk() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.of(new UserEntity()));
        when(roleService.findById(2L)).thenReturn(Optional.of(new RoleEntity()));
        mockMvc.perform(post("/api/users/1/roles/2"))
                .andExpect(status().isOk());
    }

    @Test
    void assignRoleToUser_shouldReturnNotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.empty());
        when(roleService.findById(2L)).thenReturn(Optional.of(new RoleEntity()));
        mockMvc.perform(post("/api/users/1/roles/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeRoleFromUser_shouldReturnOk() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.of(new UserEntity()));
        when(roleService.findById(2L)).thenReturn(Optional.of(new RoleEntity()));
        mockMvc.perform(delete("/api/users/1/roles/2"))
                .andExpect(status().isOk());
    }

    @Test
    void removeRoleFromUser_shouldReturnNotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.of(new UserEntity()));
        when(roleService.findById(2L)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/users/1/roles/2"))
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
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setUsername("user");
        when(userService.findById(1L)).thenReturn(Optional.of(entity));
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    void getUserById_shouldReturnNotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void createUser_shouldReturnCreated() throws Exception {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setUsername("user");
        when(userService.save(any())).thenReturn(entity);
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    void updateUser_shouldReturnUpdated() throws Exception {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setUsername("user");
        when(userService.findById(1L)).thenReturn(Optional.of(entity));
        when(userService.save(any())).thenReturn(entity);
        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    void updateUser_shouldReturnNotFound() throws Exception {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setUsername("user");
        when(userService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(entity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void deleteUser_shouldReturnNoContent() throws Exception {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        when(userService.findById(1L)).thenReturn(Optional.of(entity));
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_shouldReturnNotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNotFound());
    }
}
