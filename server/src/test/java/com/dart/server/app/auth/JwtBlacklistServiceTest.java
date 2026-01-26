package com.dart.server.app.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtBlacklistServiceTest {
    private JwtBlacklistService blacklistService;

    @BeforeEach
    void setUp() {
        blacklistService = new JwtBlacklistService();
    }

    @Test
    void blacklistToken_shouldAddToken() {
        String token = "test-token";
        blacklistService.blacklistToken(token);
        assertTrue(blacklistService.isBlacklisted(token));
    }

    @Test
    void isBlacklisted_shouldReturnFalseForUnknownToken() {
        assertFalse(blacklistService.isBlacklisted("unknown-token"));
    }

    @Test
    void blacklistToken_shouldHandleDuplicateTokens() {
        String token = "duplicate-token";
        blacklistService.blacklistToken(token);
        blacklistService.blacklistToken(token);
        assertTrue(blacklistService.isBlacklisted(token));
    }

    @Test
    void blacklistToken_shouldHandleNullToken() {
        assertDoesNotThrow(() -> blacklistService.blacklistToken(null));
        assertFalse(blacklistService.isBlacklisted(null));
    }

    @Test
    void blacklistToken_shouldHandleEmptyToken() {
        blacklistService.blacklistToken("");
        assertTrue(blacklistService.isBlacklisted(""));
    }

    @Test
    void isBlacklisted_shouldBeThreadSafe() throws InterruptedException {
        String token = "thread-token";
        Runnable task = () -> blacklistService.blacklistToken(token);
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        assertTrue(blacklistService.isBlacklisted(token));
    }
}
