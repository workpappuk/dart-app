package com.dart.server.config.db;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuditorAwareImplTest {
    @Test
    void getCurrentAuditor_returnsSystemWhenAuthNull() {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            SecurityContext context = mock(SecurityContext.class);
            when(context.getAuthentication()).thenReturn(null);
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);
            AuditorAwareImpl auditor = new AuditorAwareImpl();
            assertEquals(Optional.of("SYSTEM"), auditor.getCurrentAuditor());
        }
    }

    @Test
    void getCurrentAuditor_returnsSystemWhenNotAuthenticated() {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            SecurityContext context = mock(SecurityContext.class);
            Authentication auth = mock(Authentication.class);
            when(auth.isAuthenticated()).thenReturn(false);
            when(context.getAuthentication()).thenReturn(auth);
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);
            AuditorAwareImpl auditor = new AuditorAwareImpl();
            assertEquals(Optional.of("SYSTEM"), auditor.getCurrentAuditor());
        }
    }

    @Test
    void getCurrentAuditor_returnsNameWhenAuthenticated() {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            SecurityContext context = mock(SecurityContext.class);
            Authentication auth = mock(Authentication.class);
            when(auth.isAuthenticated()).thenReturn(true);
            when(auth.getName()).thenReturn("user1");
            when(context.getAuthentication()).thenReturn(auth);
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);
            AuditorAwareImpl auditor = new AuditorAwareImpl();
            assertEquals(Optional.of("user1"), auditor.getCurrentAuditor());
        }
    }

    @Test
    void getCurrentAuditor_returnsEmptyWhenAuthenticatedButNameNull() {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            SecurityContext context = mock(SecurityContext.class);
            Authentication auth = mock(Authentication.class);
            when(auth.isAuthenticated()).thenReturn(true);
            when(auth.getName()).thenReturn(null);
            when(context.getAuthentication()).thenReturn(auth);
            mocked.when(SecurityContextHolder::getContext).thenReturn(context);
            AuditorAwareImpl auditor = new AuditorAwareImpl();
            assertEquals(Optional.ofNullable(null), auditor.getCurrentAuditor());
        }
    }
}

