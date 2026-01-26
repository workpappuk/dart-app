package com.dart.server.app.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionRepository extends JpaRepository<PermissionEntity, UUID> {
    PermissionEntity findByName(String name);

}