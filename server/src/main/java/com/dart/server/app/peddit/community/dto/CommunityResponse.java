package com.dart.server.app.peddit.community.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CommunityResponse {
    private UUID id;
    private String name;
    private String description;
    private String createdBy;
    private boolean markedForDeletion;
}

