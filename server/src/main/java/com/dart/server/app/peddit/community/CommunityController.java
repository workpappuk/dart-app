package com.dart.server.app.peddit.community;

import com.dart.server.app.peddit.community.dto.CommunityRequest;
import com.dart.server.app.peddit.community.dto.CommunityResponse;
import com.dart.server.common.response.DartApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import java.util.List;

@RestController
@RequestMapping("/api/communities")
@Tag(name = "Communities", description = "Endpoints for managing communities")
public class CommunityController {
    @Autowired
    private CommunityService communityService;

    @Operation(summary = "Get all communities", description = "Returns a list of all communities.")
    @GetMapping
    public DartApiResponse<List<CommunityResponse>> getAll() {
        return DartApiResponse.<List<CommunityResponse>>builder()
                .success(true)
                .message("OK")
                .data(communityService.getAll())
                .build();
    }

    @Operation(summary = "Get community by ID", description = "Returns a single community by its ID.")
    @GetMapping("/{id}")
    public DartApiResponse<CommunityResponse> getById(@PathVariable UUID id) {
        CommunityResponse response = communityService.getById(id);
        if (response == null)
            return DartApiResponse.<CommunityResponse>builder().success(false).message("Community not found").build();
        return DartApiResponse.<CommunityResponse>builder().success(true).message("OK").data(response).build();
    }

    @Operation(summary = "Create a new community", description = "Creates a new community.")
    @PostMapping
    public DartApiResponse<CommunityResponse> create(@RequestBody CommunityRequest request) {
        return DartApiResponse.<CommunityResponse>builder()
                .success(true)
                .message("Created")
                .data(communityService.create(request))
                .build();
    }

    @Operation(summary = "Update a community", description = "Updates an existing community by ID.")
    @PutMapping("/{id}")
    public DartApiResponse<CommunityResponse> update(@PathVariable UUID id, @RequestBody CommunityRequest request) {
        CommunityResponse response = communityService.update(id, request);
        if (response == null)
            return DartApiResponse.<CommunityResponse>builder().success(false).message("Community not found").build();
        return DartApiResponse.<CommunityResponse>builder().success(true).message("OK").data(response).build();
    }

    @Operation(summary = "Delete a community", description = "Marks a community as deleted by ID.")
    @DeleteMapping("/{id}")
    public DartApiResponse<Boolean> delete(@PathVariable UUID id) {
        var entity = communityService.getByIdEntity(id);
        if (entity == null)
            return DartApiResponse.<Boolean>builder().success(false).message("Community not found").build();
        if (entity.isMarkedForDeletion()) {
            return DartApiResponse.<Boolean>builder().success(false).message("Community already marked for deletion").build();
        }
        entity.setMarkedForDeletion(true);
        communityService.save(entity);
        return DartApiResponse.<Boolean>builder().success(true).message("Marked for deletion").data(true).build();
    }
}
