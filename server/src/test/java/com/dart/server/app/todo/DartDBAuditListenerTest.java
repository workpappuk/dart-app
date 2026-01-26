package com.dart.server.app.todo;

import com.dart.server.app.auth.UserEntity;
import com.dart.server.app.auth.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DartDBAuditListenerTest {
    @Mock
    UserRepository userRepository;
    AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        // Set static userRepository for the listener
        ReflectionTestUtils.setField(DartDBAuditListener.class, "userRepository", userRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
        // Reset static field
        ReflectionTestUtils.setField(DartDBAuditListener.class, "userRepository", null);
    }

    @Test
    void setCreatedBy_setsUserIfPresent() {
        DartDBAuditListener listener = new DartDBAuditListener();
        TodoEntity entity = new TodoEntity();
        UserEntity user = new UserEntity();
        user.setId(1L);
        // Mock AuthUtils.getCurrentUserId() via static mocking
        try (var mocked = Mockito.mockStatic(com.dart.server.common.utils.AuthUtils.class)) {
            mocked.when(com.dart.server.common.utils.AuthUtils::getCurrentUsername).thenReturn("1");
            when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
            listener.setCreatedBy(entity);
            assertEquals(user, entity.getCreatedBy());
            assertEquals(user, entity.getUpdatedBy());
        }
    }

    @Test
    void setUpdatedBy_setsUserIfPresent() {
        DartDBAuditListener listener = new DartDBAuditListener();
        TodoEntity entity = new TodoEntity();
        UserEntity user = new UserEntity();
        user.setId(2L);
        try (var mocked = Mockito.mockStatic(com.dart.server.common.utils.AuthUtils.class)) {
            mocked.when(com.dart.server.common.utils.AuthUtils::getCurrentUsername).thenReturn("2");
            when(userRepository.findById(2L)).thenReturn(java.util.Optional.of(user));
            listener.setUpdatedBy(entity);
            assertEquals(user, entity.getUpdatedBy());
        }
    }

    @Test
    void setCreatedBy_handlesNullUser() {
        DartDBAuditListener listener = new DartDBAuditListener();
        TodoEntity entity = new TodoEntity();
        try (var mocked = Mockito.mockStatic(com.dart.server.common.utils.AuthUtils.class)) {
            mocked.when(com.dart.server.common.utils.AuthUtils::getCurrentUsername).thenReturn(null);
            listener.setCreatedBy(entity);
            assertNull(entity.getCreatedBy());
            assertNull(entity.getUpdatedBy());
        }
    }

    @Test
    void setUpdatedBy_handlesNullUser() {
        DartDBAuditListener listener = new DartDBAuditListener();
        TodoEntity entity = new TodoEntity();
        try (var mocked = Mockito.mockStatic(com.dart.server.common.utils.AuthUtils.class)) {
            mocked.when(com.dart.server.common.utils.AuthUtils::getCurrentUsername).thenReturn(null);
            listener.setUpdatedBy(entity);
            assertNull(entity.getUpdatedBy());
        }
    }

    @Test
    void getCurrentUserEntity_handlesNumberFormatException() {
        DartDBAuditListener listener = new DartDBAuditListener();
        TodoEntity entity = new TodoEntity();
        try (var mocked = Mockito.mockStatic(com.dart.server.common.utils.AuthUtils.class)) {
            mocked.when(com.dart.server.common.utils.AuthUtils::getCurrentUsername).thenReturn("notANumber");
            // Should not throw
            listener.setCreatedBy(entity);
            assertNull(entity.getCreatedBy());
        }
    }
}

