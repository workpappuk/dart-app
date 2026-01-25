package com.dart.server.app.peddit.post;

import com.dart.server.app.peddit.post.dto.PostRequest;
import com.dart.server.app.peddit.post.dto.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Endpoints for managing posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Operation(summary = "Get all posts", description = "Returns a list of all posts.")
    @GetMapping
    public List<PostResponse> getAll() {
        return postService.getAll();
    }

    @Operation(summary = "Get post by ID", description = "Returns a single post by its ID.")
    @GetMapping("/{id}")
    public PostResponse getById(@PathVariable Long id) {
        return postService.getById(id);
    }

    @Operation(summary = "Create a new post", description = "Creates a new post.")
    @PostMapping
    public PostResponse create(@RequestBody PostRequest request) {
        return postService.create(request);
    }

    @Operation(summary = "Update a post", description = "Updates an existing post by ID.")
    @PutMapping("/{id}")
    public PostResponse update(@PathVariable Long id, @RequestBody PostRequest request) {
        return postService.update(id, request);
    }

    @Operation(summary = "Delete a post", description = "Marks a post as deleted by ID.")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return postService.delete(id);
    }
}
