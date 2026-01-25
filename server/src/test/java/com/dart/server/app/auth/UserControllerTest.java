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
}
