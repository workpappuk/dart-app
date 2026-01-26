package com.dart.server.app.peddit.post;

import com.dart.server.app.peddit.post.dto.PostRequest;
import com.dart.server.app.peddit.post.dto.PostResponse;
import com.dart.server.common.response.DartApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Endpoints for managing posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Operation(summary = "Get all posts", description = "Returns a list of all posts.")
    @GetMapping
    public DartApiResponse<List<PostResponse>> getAll() {
        return DartApiResponse.<List<PostResponse>>builder()
                .success(true)
                .message("OK")
                .data(postService.getAll())
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
    public DartApiResponse<PostResponse> create(@RequestBody PostRequest request) {
        return DartApiResponse.<PostResponse>builder()
                .success(true)
                .message("Created")
                .data(postService.create(request))
                .build();
    }

    @Operation(summary = "Update a post", description = "Updates an existing post by ID.")
    @PutMapping("/{id}")
    public DartApiResponse<PostResponse> update(@PathVariable UUID id, @RequestBody PostRequest request) {
        PostResponse response = postService.update(id, request);
        if (response == null)
            return DartApiResponse.<PostResponse>builder().success(false).message("Post not found").build();
        return DartApiResponse.<PostResponse>builder().success(true).message("OK").data(response).build();
    }

    @Operation(summary = "Delete a post", description = "Marks a post as deleted by ID.")
    @DeleteMapping("/{id}")
    public DartApiResponse<Boolean> delete(@PathVariable UUID id) {
        var entity = postService.getByIdEntity(id);
        if (entity == null) return DartApiResponse.<Boolean>builder().success(false).message("Post not found").build();
        if (entity.isMarkedForDeletion()) {
            return DartApiResponse.<Boolean>builder().success(false).message("Post already marked for deletion").build();
        }
        entity.setMarkedForDeletion(true);
        postService.save(entity);
        return DartApiResponse.<Boolean>builder().success(true).message("Marked for deletion").data(true).build();
    }
}
