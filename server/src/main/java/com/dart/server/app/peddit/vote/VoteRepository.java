package com.dart.server.app.peddit.vote;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoteRepository extends JpaRepository<VoteEntity, UUID> {
}

