package com.dart.server.app.auth;

import com.dart.server.app.auth.RoleEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret:secret-key}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}") // 1 day default
    private long jwtExpirationInMs;

    public String createToken(String username, Set<RoleEntity> roles) {
        String rolesString = roles.stream()
                .map(RoleEntity::getName)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", rolesString)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Optionally, add methods to validate and parse the token
}
