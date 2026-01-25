package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthControllerTest {
    @Mock
    UserService userService;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @InjectMocks
    AuthController authController;

    @Test
    void contextLoads() throws Exception {
        MockitoAnnotations.openMocks(this);
        assertNotNull(userService);
        assertNotNull(jwtTokenProvider);
        assertNotNull(authController);
    }
}
