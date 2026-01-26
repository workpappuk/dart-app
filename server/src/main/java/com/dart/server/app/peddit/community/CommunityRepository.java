package com.dart.server.app.peddit.community;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommunityRepository extends JpaRepository<CommunityEntity, UUID> {
}

