package com.dart.server.app.peddit.comment;

import com.dart.server.app.peddit.comment.dto.CommentRequest;
import com.dart.server.app.peddit.comment.dto.CommentResponse;
import com.dart.server.common.response.DartApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "Comments", description = "Endpoints for managing comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Operation(summary = "Get all comments", description = "Returns a list of all comments.")
    @GetMapping
    public DartApiResponse<List<CommentResponse>> getAll() {
        return DartApiResponse.<List<CommentResponse>>builder()
                .success(true)
                .message("OK")
                .data(commentService.getAll())
                .build();
    }

    @Operation(summary = "Get comment by ID", description = "Returns a single comment by its ID.")
    @GetMapping("/{id}")
    public DartApiResponse<CommentResponse> getById(@PathVariable Long id) {
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
    public DartApiResponse<CommentResponse> update(@PathVariable Long id, @RequestBody CommentRequest request) {
        CommentResponse response = commentService.update(id, request);
        if (response == null)
            return DartApiResponse.<CommentResponse>builder().success(false).message("Comment not found").build();
        return DartApiResponse.<CommentResponse>builder().success(true).message("OK").data(response).build();
    }

    @Operation(summary = "Delete a comment", description = "Marks a comment as deleted by ID.")
    @DeleteMapping("/{id}")
    public DartApiResponse<Boolean> delete(@PathVariable Long id) {
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
