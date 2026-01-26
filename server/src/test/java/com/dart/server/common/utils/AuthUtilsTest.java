package com.dart.server.common.utils;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthUtilsTest {
    @Test
    void getCurrentUsername_returnsUsername() {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            SecurityContext context = mock(SecurityContext.class);
            Authentication auth = mock(Authentication.class);
            when(auth.isAuthenticated()).thenReturn(true);
            when(auth.getName()).thenReturn("user");
            when(context.getAuthentication()).thenReturn(auth);
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);
            assertEquals("user", AuthUtils.getCurrentUsername());
        }
    }

    @Test
    void getCurrentUsername_returnsNullOnException() {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            mocked.when(SecurityContextHolder::getContext).thenThrow(new RuntimeException());
            assertNull(AuthUtils.getCurrentUsername());
        }
    }

    @Test
    void getCurrentUsername_returnsNullWhenAuthIsNull() {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            SecurityContext context = mock(SecurityContext.class);
            when(context.getAuthentication()).thenReturn(null);
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);
            assertNull(AuthUtils.getCurrentUsername());
        }
    }

    @Test
    void getCurrentUsername_returnsNullWhenNotAuthenticated() {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            SecurityContext context = mock(SecurityContext.class);
            Authentication auth = mock(Authentication.class);
            when(auth.isAuthenticated()).thenReturn(false);
            when(context.getAuthentication()).thenReturn(auth);
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);
            assertNull(AuthUtils.getCurrentUsername());
        }
    }

    @Test
    void getCurrentUsername_returnsNullWhenNameIsNull() {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            SecurityContext context = mock(SecurityContext.class);
            Authentication auth = mock(Authentication.class);
            when(auth.isAuthenticated()).thenReturn(true);
            when(auth.getName()).thenReturn(null);
            when(context.getAuthentication()).thenReturn(auth);
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);
            assertNull(AuthUtils.getCurrentUsername());
        }
    }

    @Test
    void isAdmin_returnsTrueForAdminRole() {
        Authentication auth = mock(Authentication.class);
        when(auth.getAuthorities()).thenReturn((java.util.Set) java.util.Set.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ADMIN")));
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            SecurityContext context = mock(SecurityContext.class);
            when(context.getAuthentication()).thenReturn(auth);
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);
            assertTrue(AuthUtils.isAdmin());
        }
    }

    @Test
    void isAdmin_returnsFalseForNonAdminRole() {
        Authentication auth = mock(Authentication.class);
        when(auth.getAuthorities()).thenReturn((java.util.Set) java.util.Set.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("USER")));
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            SecurityContext context = mock(SecurityContext.class);
            when(context.getAuthentication()).thenReturn(auth);
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);
            assertFalse(AuthUtils.isAdmin());
        }
    }

    @Test
    void getUser_returnsUserEntity() {
        com.dart.server.app.auth.UserRepository repo = mock(com.dart.server.app.auth.UserRepository.class);
        com.dart.server.app.auth.UserEntity user = new com.dart.server.app.auth.UserEntity();
        when(repo.findByUsername("user")).thenReturn(java.util.Optional.of(user));
        assertEquals(user, AuthUtils.getUser("user", repo));
    }

    @Test
    void getUser_returnsNullIfNotFound() {
        com.dart.server.app.auth.UserRepository repo = mock(com.dart.server.app.auth.UserRepository.class);
        when(repo.findByUsername("user")).thenReturn(java.util.Optional.empty());
        assertNull(AuthUtils.getUser("user", repo));
    }

    @Test
    void isOwner_returnsTrueIfOwner() {
        com.dart.server.app.todo.TodoEntity todo = new com.dart.server.app.todo.TodoEntity();
        com.dart.server.app.auth.UserEntity user = new com.dart.server.app.auth.UserEntity();
        java.util.UUID id = java.util.UUID.randomUUID();
        user.setId(id);
        todo.setCreatedBy(id.toString());
        assertTrue(AuthUtils.isOwner(todo, user));
    }

    @Test
    void isOwner_returnsFalseIfNotOwner() {
        com.dart.server.app.todo.TodoEntity todo = new com.dart.server.app.todo.TodoEntity();
        com.dart.server.app.auth.UserEntity user = new com.dart.server.app.auth.UserEntity();
        java.util.UUID id = java.util.UUID.randomUUID();
        user.setId(id);
        todo.setCreatedBy(java.util.UUID.randomUUID().toString());
        assertFalse(AuthUtils.isOwner(todo, user));
    }

    @Test
    void isOwner_returnsFalseIfUserNullOrCreatedByNullOrUserIdNull() {
        com.dart.server.app.todo.TodoEntity todo = new com.dart.server.app.todo.TodoEntity();
        com.dart.server.app.auth.UserEntity user = new com.dart.server.app.auth.UserEntity();
        assertFalse(AuthUtils.isOwner(todo, null));
        assertFalse(AuthUtils.isOwner(todo, user));
        todo.setCreatedBy(null);
        user.setId(java.util.UUID.randomUUID());
        assertFalse(AuthUtils.isOwner(todo, user));
    }
}
