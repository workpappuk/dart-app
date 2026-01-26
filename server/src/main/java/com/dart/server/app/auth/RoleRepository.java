package com.dart.server.app.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    boolean existsByName(String name);

    java.util.Optional<RoleEntity> findByName(String name);
}