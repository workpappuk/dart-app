package com.dart.server.app.peddit.comment;

import com.dart.server.app.peddit.comment.dto.CommentRequest;
import com.dart.server.app.peddit.comment.dto.CommentResponse;
import com.dart.server.app.todo.dto.TodoResponse;
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
@RequestMapping("/api/comments")
@Tag(name = "Comments", description = "Endpoints for managing comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Operation(summary = "Search comments", description = "Search comments by description with pagination.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paginated list of comments returned successfully")
    })
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public DartApiResponse<PageResponse<CommentResponse>> searchComments(
            @Parameter(description = "Search query") @RequestParam(defaultValue = "") String q,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        Page<CommentResponse> result = commentService.searchComments(q, page, size);
        PageResponse<CommentResponse> pageResponse = new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
        return DartApiResponse.<PageResponse<CommentResponse>>builder()
                .success(true)
                .message("Comments search result fetched successfully")
                .data(pageResponse)
                .build();
    }

    @Operation(summary = "Get comment by ID", description = "Returns a single comment by its ID.")
    @GetMapping("/{id}")
    public DartApiResponse<CommentResponse> getById(@PathVariable UUID id) {
        CommentResponse response = commentService.getById(id);
        if (response == null)
            return DartApiResponse.<CommentResponse>builder().success(false).message("Comment not found").build();
        return DartApiResponse.<CommentResponse>builder().success(true).message("OK").data(response).build();
    }

    @Operation(summary = "Create a new comment", description = "Creates a new comment.")
    @PostMapping
    public DartApiResponse<CommentResponse> create(@RequestBody CommentRequest request) {
        return DartApiResponse.<CommentResponse>builder()
                .success(true)
                .message("Created")
                .data(commentService.create(request))
                .build();
    }

    @Operation(summary = "Update a comment", description = "Updates an existing comment by ID.")
    @PutMapping("/{id}")
    public DartApiResponse<CommentResponse> update(@PathVariable UUID id, @RequestBody CommentRequest request) {
        CommentResponse response = commentService.update(id, request);
        if (response == null)
            return DartApiResponse.<CommentResponse>builder().success(false).message("Comment not found").build();
        return DartApiResponse.<CommentResponse>builder().success(true).message("OK").data(response).build();
    }

    @Operation(summary = "Delete a comment", description = "Marks a comment as deleted by ID.")
    @DeleteMapping("/{id}")
    public DartApiResponse<Boolean> delete(@PathVariable UUID id) {
        var entity = commentService.getByIdEntity(id);
        if (entity == null)
            return DartApiResponse.<Boolean>builder().success(false).message("Comment not found").build();
        if (entity.isMarkedForDeletion()) {
            return DartApiResponse.<Boolean>builder().success(false).message("Comment already marked for deletion").build();
        }
        entity.setMarkedForDeletion(true);
        commentService.save(entity);
        return DartApiResponse.<Boolean>builder().success(true).message("Marked for deletion").data(true).build();
    }
}
