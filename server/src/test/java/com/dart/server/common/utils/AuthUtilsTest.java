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
}
