package com.dart.server.app.peddit.post;

import com.dart.server.app.peddit.comment.dto.CommentResponse;
import com.dart.server.app.peddit.post.dto.PostRequest;
import com.dart.server.app.peddit.post.dto.PostResponse;
import com.dart.server.common.response.DartApiResponse;
import com.dart.server.common.response.PageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Endpoints for managing posts")
public class PostController {
    @Autowired
    private PostService postService;

     @Operation(summary = "Search posts", description = "Search posts by description with pagination.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paginated list of posts returned successfully")
    })
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public DartApiResponse<PageResponse<PostResponse>> searchPosts(
            @Parameter(description = "Search query") @RequestParam(defaultValue = "") String q,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        Page<PostResponse> result = postService.searchPosts(q, page, size);
        PageResponse<PostResponse> pageResponse = new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
        return DartApiResponse.<PageResponse<PostResponse>>builder()
                .success(true)
                .message("Posts search result fetched successfully")
                .data(pageResponse)
                .build();
    }

    @Operation(summary = "Get post by ID", description = "Returns a single post by its ID.")
    @GetMapping("/{id}")
    public DartApiResponse<PostResponse> getById(@PathVariable UUID id) {
        PostResponse response = postService.getById(id);
        if (response == null)
            return DartApiResponse.<PostResponse>builder().success(false).message("Post not found").build();
        return DartApiResponse.<PostResponse>builder().success(true).message("OK").data(response).build();
    }

    @Operation(summary = "Create a new post", description = "Creates a new post.")
    @PostMapping
    public ResponseEntity<DartApiResponse<PostResponse>> create(@RequestBody @Valid PostRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().body(
                    DartApiResponse.<PostResponse>builder()
                            .success(false)
                            .message("Request body is empty")
                            .build()
            );
        }
        PostResponse response = postService.create(request);
        if (response == null) {
            return ResponseEntity.ok(
                    DartApiResponse.<PostResponse>builder()
                            .success(false)
                            .message("Post creation failed")
                            .build()
            );
        }
        return ResponseEntity.ok(
                DartApiResponse.<PostResponse>builder()
                .success(true)
                .message("Created")
                        .data(response)
                        .build()
        );
    }

    @Operation(summary = "Update a post", description = "Updates an existing post by ID.")
    @PutMapping("/{id}")
    public ResponseEntity<DartApiResponse<PostResponse>> update(@PathVariable UUID id, @RequestBody(required = false) PostRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().body(
                    DartApiResponse.<PostResponse>builder()
                            .success(false)
                            .message("Request body is empty")
                            .build()
            );
        }
        PostResponse response = postService.update(id, request);
        if (response == null) {
            return ResponseEntity.ok(
                    DartApiResponse.<PostResponse>builder()
                            .success(false)
                            .message("Post not found")
                            .build()
            );
        }
        return ResponseEntity.ok(
                DartApiResponse.<PostResponse>builder().success(true).message("OK").data(response).build()
        );
    }

    @Operation(summary = "Delete a post", description = "Marks a post as deleted by ID.")
    @DeleteMapping("/{id}")
    public DartApiResponse<Boolean> delete(@PathVariable UUID id) {
        var entity = postService.getByIdEntity(id);
        if (entity == null) {
            return DartApiResponse.<Boolean>builder().success(false).message("Post not found").build();
        }
        if (entity.isMarkedForDeletion()) {
            return DartApiResponse.<Boolean>builder().success(false).message("Post already marked for deletion").build();
        }
        entity.setMarkedForDeletion(true);
        postService.save(entity);
        return DartApiResponse.<Boolean>builder().success(true).message("Marked for deletion").data(true).build();
    }
}
