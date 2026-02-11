package com.dart.server.app.peddit.community;

import com.dart.server.app.peddit.community.dto.CommunityRequest;
import com.dart.server.app.peddit.community.dto.CommunityResponse;
import com.dart.server.app.peddit.post.dto.PostResponse;
import com.dart.server.common.response.DartApiResponse;
import com.dart.server.common.response.PageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import java.util.List;

@RestController
@RequestMapping("/api/communities")
@Tag(name = "Communities", description = "Endpoints for managing communities")
public class CommunityController {
    @Autowired
    private CommunityService communityService;

     @Operation(summary = "Search communities", description = "Search communities by description with pagination.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paginated list of communities returned successfully")
    })
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public DartApiResponse<PageResponse<CommunityResponse>> searchCommunities(
            @Parameter(description = "Search query") @RequestParam(defaultValue = "") String q,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        Page<CommunityResponse> result = communityService.searchCommunities(q, page, size);
        PageResponse<CommunityResponse> pageResponse = new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
        return DartApiResponse.<PageResponse<CommunityResponse>>builder()
                .success(true)
                .message("Communities search result fetched successfully")
                .data(pageResponse)
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
