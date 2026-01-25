package com.dart.server.app.peddit.vote;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
@Tag(name = "Votes", description = "Endpoints for managing votes")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @Operation(summary = "Get all votes", description = "Returns a list of all votes.")
    @GetMapping
    public List<VoteEntity> getAll() {
        return voteService.getAll();
    }

    @Operation(summary = "Get vote by ID", description = "Returns a single vote by its ID.")
    @GetMapping("/{id}")
    public VoteEntity getById(@PathVariable Long id) {
        return voteService.getById(id);
    }

    @Operation(summary = "Create a new vote", description = "Creates a new vote.")
    @PostMapping
    public VoteEntity create(@RequestBody VoteEntity vote) {
        return voteService.create(vote);
    }

    @Operation(summary = "Delete a vote", description = "Deletes a vote by ID.")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return voteService.delete(id);
    }
}
