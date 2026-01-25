package com.dart.server.app.peddit.community.dto;

import lombok.Data;

@Data
public class CommunityResponse {
    private Long id;
    private String name;
    private String description;
    private String createdBy;
    private boolean markedForDeletion;
}

