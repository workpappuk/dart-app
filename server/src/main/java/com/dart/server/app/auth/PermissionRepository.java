package com.dart.server.app.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    boolean existsByName(String name);
}