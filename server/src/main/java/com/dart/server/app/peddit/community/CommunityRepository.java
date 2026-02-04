package com.dart.server.app.peddit.community;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommunityRepository extends JpaRepository<CommunityEntity, UUID> {
    Page<CommunityEntity> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);

    Page<CommunityEntity> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndMarkedForDeletionFalse(String name, String description, Pageable pageable);
}
