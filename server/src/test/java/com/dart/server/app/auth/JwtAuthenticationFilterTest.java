package com.dart.server.app.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    FilterChain filterChain;
    @Mock
    Claims claims;
    @InjectMocks
    JwtAuthenticationFilter filter;

    JwtBlacklistService jwtBlacklistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
        jwtBlacklistService = new JwtBlacklistService();
        // Use reflection to inject the mock/service for testing
        try {
            java.lang.reflect.Field field = JwtAuthenticationFilter.class.getDeclaredField("jwtBlacklistService");
            field.setAccessible(true);
            field.set(filter, jwtBlacklistService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void doFilterInternal_validToken_setsAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer validtoken");
        when(jwtTokenProvider.validateToken("validtoken")).thenReturn(true);
        when(jwtTokenProvider.getClaims("validtoken")).thenReturn(claims);
        when(claims.getSubject()).thenReturn("user");
        when(claims.get("roles", String.class)).thenReturn("ROLE_USER,ROLE_ADMIN");

        filter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        assertTrue(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        assertTrue(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_invalidToken_doesNotSetAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidtoken");
        when(jwtTokenProvider.validateToken("invalidtoken")).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_noHeader_doesNotSetAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_invalidFormatHeader_doesNotSetAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("InvalidFormat");

        filter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_exceptionInTokenHandling_doesNotSetAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer sometoken");
        when(jwtTokenProvider.validateToken("sometoken")).thenThrow(new RuntimeException("fail"));

        filter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }
}
