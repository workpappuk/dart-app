package com.dart.server.app.peddit.community.dto;

import java.util.UUID;

import com.dart.server.common.dto.AuditDTOResponse;
import lombok.Data;

@Data
public class CommunityResponse extends AuditDTOResponse {
    private String name;
    private String description;
}

