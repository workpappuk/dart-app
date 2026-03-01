package com.dart.server.app.auth;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JwtBlacklistService {
    private final Set<String> blacklist = ConcurrentHashMap.newKeySet();

    public void blacklistToken(String token) {
        if (token == null) return;
        blacklist.add(token);
    }

    public boolean isBlacklisted(String token) {
        if (token == null) return false;
        return blacklist.contains(token);
    }
}
