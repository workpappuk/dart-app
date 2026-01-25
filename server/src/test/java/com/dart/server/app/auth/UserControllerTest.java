package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    @Test
    void contextLoads() throws Exception {
        MockitoAnnotations.openMocks(this);
        assertNotNull(userService);
        assertNotNull(userController);
    }
}
