package com.dart.server.app.todo;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TodoControllerTest {
    @Mock
    TodoService todoService;
    @InjectMocks
    TodoController todoController;

    @Test
    void contextLoads() {
        MockitoAnnotations.openMocks(this);
    }
}
