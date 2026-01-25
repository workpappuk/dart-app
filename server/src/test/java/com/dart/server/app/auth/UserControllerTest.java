package com.dart.server.app.auth;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @Test
    void contextLoads() {
        MockitoAnnotations.openMocks(this);
    }
}
