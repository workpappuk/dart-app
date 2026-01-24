package com.dart.server.app.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    PermissionEntity findByName(String name);

}