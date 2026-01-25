package com.dart.server.app.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {
    JwtTokenProvider provider;
    String secret;
    SecretKey key;

    @BeforeEach
    void setUp() {
        provider = new JwtTokenProvider();
        // Generate a secure 512-bit (64-byte) key for HS512
        key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
        secret = Base64.getEncoder().encodeToString(key.getEncoded());
        ReflectionTestUtils.setField(provider, "jwtSecret", secret);
        ReflectionTestUtils.setField(provider, "jwtExpirationInMs", 10000L);
    }

    @Test
    void createToken_and_getClaims_success() {
        RoleEntity role = new RoleEntity();
        role.setName("ROLE_USER");
        String token = provider.createToken(123L, Collections.singleton(role));
        Claims claims = provider.getClaims(token);
        assertEquals("123", claims.getSubject());
        assertEquals("ROLE_USER", claims.get("roles"));
    }

    @Test
    void validateToken_validToken_returnsTrue() {
        RoleEntity role = new RoleEntity();
        role.setName("ROLE_USER");
        String token = provider.createToken(123L, Collections.singleton(role));
        assertTrue(provider.validateToken(token));
    }

    @Test
    void validateToken_invalidToken_returnsFalse() {
        assertFalse(provider.validateToken("invalid.token.value"));
    }

    @Test
    void getClaims_invalidToken_throwsException() {
        assertThrows(JwtException.class, () -> provider.getClaims("invalid.token.value"));
    }

    @Test
    void createToken_expiredToken_validateTokenReturnsFalse() throws InterruptedException {
        ReflectionTestUtils.setField(provider, "jwtExpirationInMs", 1L);
        RoleEntity role = new RoleEntity();
        role.setName("ROLE_USER");
        String token = provider.createToken(123L, Collections.singleton(role));
        Thread.sleep(10); // ensure token is expired
        boolean valid = false;
        try {
            valid = provider.validateToken(token);
        } catch (Exception e) {
            valid = false;
        }
        assertFalse(valid);
    }
}
