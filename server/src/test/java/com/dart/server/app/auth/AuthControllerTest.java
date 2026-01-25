package com.dart.server.app.auth;

import com.dart.server.app.auth.dto.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {
    private MockMvc mockMvc;
    @Mock
    UserService userService;
    @Mock
    RoleService roleService;
    @Mock
    org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @InjectMocks
    AuthController authController;

    @SuppressWarnings("resource")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void debugAuth_shouldReturn200() throws Exception {
        Authentication mockAuth = org.mockito.Mockito.mock(Authentication.class);
        org.mockito.Mockito.when(mockAuth.getAuthorities()).thenReturn(java.util.Collections.emptyList());
        mockMvc.perform(get("/api/auth/debug-auth").with(SecurityMockMvcRequestPostProcessors.authentication(mockAuth)))
                .andExpect(status().isOk());
    }

    @Test
    void debugAuth_shouldHandleNullAuthentication() throws Exception {
        // No authentication set in context
        mockMvc.perform(get("/api/auth/debug-auth"))
                .andExpect(status().isOk());
    }

    @Test
    void debugAuth_shouldHandleNullAuthorities() throws Exception {
        Authentication mockAuth = org.mockito.Mockito.mock(Authentication.class);
        org.mockito.Mockito.when(mockAuth.getAuthorities()).thenReturn(null);
        mockMvc.perform(get("/api/auth/debug-auth").with(SecurityMockMvcRequestPostProcessors.authentication(mockAuth)))
                .andExpect(status().isOk());
    }

    @Test
    void register_shouldReturnSuccess() throws Exception {
        UserRequest req = new UserRequest();
        req.setUsername("user");
        req.setPassword("pass");
        when(userService.existsByUsername(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(roleService.findByName(any())).thenReturn(new RoleEntity());
        when(userService.save(any())).thenReturn(new UserEntity());
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void register_shouldReturnUsernameExists() throws Exception {
        UserRequest req = new UserRequest();
        req.setUsername("user");
        req.setPassword("pass");
        when(userService.existsByUsername(any())).thenReturn(true);
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Username already exists"));
    }

    @Test
    void login_shouldReturnSuccess() throws Exception {
        UserRequest req = new UserRequest();
        req.setUsername("user");
        req.setPassword("pass");
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("user");
        user.setPassword("encoded");
        when(userService.findByUsername("user")).thenReturn(user);
        when(passwordEncoder.matches("pass", "encoded")).thenReturn(true);
        when(jwtTokenProvider.createToken(any(), any())).thenReturn("token123");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").value("token123"));
    }

    @Test
    void login_shouldReturnInvalidCredentials() throws Exception {
        UserRequest req = new UserRequest();
        req.setUsername("user");
        req.setPassword("wrong");
        when(userService.findByUsername("user")).thenReturn(null);
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid credentials"));
    }

    @Test
    void login_shouldReturnInvalidCredentialsWhenPasswordDoesNotMatch() throws Exception {
        UserRequest req = new UserRequest();
        req.setUsername("user");
        req.setPassword("wrong");
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("user");
        user.setPassword("encoded");
        when(userService.findByUsername("user")).thenReturn(user);
        when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid credentials"));
    }
}
