package com.dart.server.app.peddit.comment;

import com.dart.server.app.peddit.comment.dto.CommentRequest;
import com.dart.server.app.peddit.comment.dto.CommentResponse;
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
    public List<CommentResponse> getAll() {
        return commentService.getAll();
    }

    @Operation(summary = "Get comment by ID", description = "Returns a single comment by its ID.")
    @GetMapping("/{id}")
    public CommentResponse getById(@PathVariable Long id) {
        return commentService.getById(id);
    }

    @Operation(summary = "Create a new comment", description = "Creates a new comment.")
    @PostMapping
    public CommentResponse create(@RequestBody CommentRequest request) {
        return commentService.create(request);
    }

    @Operation(summary = "Update a comment", description = "Updates an existing comment by ID.")
    @PutMapping("/{id}")
    public CommentResponse update(@PathVariable Long id, @RequestBody CommentRequest request) {
        return commentService.update(id, request);
    }

    @Operation(summary = "Delete a comment", description = "Marks a comment as deleted by ID.")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return commentService.delete(id);
    }
}
