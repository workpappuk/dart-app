package com.dart.server.common.utils;

import com.dart.server.app.auth.ERole;
import com.dart.server.app.auth.UserEntity;
import com.dart.server.app.auth.UserRepository;
import com.dart.server.app.todo.TodoEntity;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthUtilsExtraTest {
    @Test
    void isAdmin_returnsTrueIfAdmin() {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            SecurityContext context = mock(SecurityContext.class);
            Authentication auth = mock(Authentication.class);
            GrantedAuthority admin = mock(GrantedAuthority.class);
            when(admin.getAuthority()).thenReturn(ERole.ADMIN.name());
            when(auth.getAuthorities()).thenReturn((java.util.Collection) List.of(admin)); // fix: cast to Collection
            when(context.getAuthentication()).thenReturn(auth);
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);
            assertTrue(AuthUtils.isAdmin());
        }
    }

    @Test
    void isAdmin_returnsFalseIfNotAdmin() {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            SecurityContext context = mock(SecurityContext.class);
            Authentication auth = mock(Authentication.class);
            GrantedAuthority user = mock(GrantedAuthority.class);
            when(user.getAuthority()).thenReturn("USER");
            when(auth.getAuthorities()).thenReturn((java.util.Collection) List.of(user)); // fix: cast to Collection
            when(context.getAuthentication()).thenReturn(auth);
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);
            assertFalse(AuthUtils.isAdmin());
        }
    }

    @Test
    void getUser_returnsUserIfFound() {
        UserRepository repo = mock(UserRepository.class);
        UserEntity user = new UserEntity();
        when(repo.findByUsername("foo")).thenReturn(Optional.of(user));
        assertEquals(user, AuthUtils.getUser("foo", repo));
    }

    @Test
    void getUser_returnsNullIfNotFound() {
        UserRepository repo = mock(UserRepository.class);
        when(repo.findByUsername("foo")).thenReturn(Optional.empty());
        assertNull(AuthUtils.getUser("foo", repo));
    }

    @Test
    void isOwner_returnsTrueIfOwner() {
        TodoEntity todo = new TodoEntity();
        UserEntity user = new UserEntity();
        UUID id = UUID.randomUUID();
        user.setId(id);
        todo.setCreatedBy(id.toString()); // fix: createdBy is String
        assertTrue(AuthUtils.isOwner(todo, user));
    }

    @Test
    void isOwner_returnsFalseIfNotOwner() {
        TodoEntity todo = new TodoEntity();
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        todo.setCreatedBy(UUID.randomUUID().toString()); // fix: createdBy is String
        assertFalse(AuthUtils.isOwner(todo, user));
    }

    @Test
    void isOwner_returnsFalseIfUserNull() {
        TodoEntity todo = new TodoEntity();
        todo.setCreatedBy(UUID.randomUUID().toString()); // fix: createdBy is String
        assertFalse(AuthUtils.isOwner(todo, null));
    }

    @Test
    void isOwner_returnsFalseIfCreatedByNull() {
        TodoEntity todo = new TodoEntity();
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        todo.setCreatedBy(null);
        assertFalse(AuthUtils.isOwner(todo, user));
    }
}
