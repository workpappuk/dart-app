package com.dart.server.app.todo;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TodoServiceTest {
    @Mock
    TodoRepository todoRepository;
    @Mock
    com.dart.server.app.auth.UserRepository userRepository;
    @InjectMocks
    TodoService todoService;

    @Test
    void contextLoads() throws Exception {
        try (var mocks = MockitoAnnotations.openMocks(this)) {
            // Use the fields to avoid warnings
            assertNotNull(todoRepository);
            assertNotNull(userRepository);
            assertNotNull(todoService);
        }
    }
}
