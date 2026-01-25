package com.dart.server.app.peddit.vote;

import com.dart.server.app.peddit.vote.dto.VoteRequest;
import com.dart.server.app.peddit.vote.dto.VoteResponse;
import com.dart.server.app.peddit.vote.dto.VoteMapper;
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
    public List<VoteResponse> getAll() {
        return voteService.getAll().stream().map(VoteMapper::toResponse).toList();
    }

    @Operation(summary = "Get vote by ID", description = "Returns a single vote by its ID.")
    @GetMapping("/{id}")
    public VoteResponse getById(@PathVariable Long id) {
        return VoteMapper.toResponse(voteService.getById(id));
    }

    @Operation(summary = "Create a new vote", description = "Creates a new vote.")
    @PostMapping
    public VoteResponse create(@RequestBody VoteRequest request) {
        // UserEntity should be set in service based on userId
        return VoteMapper.toResponse(voteService.create(VoteMapper.toEntity(request)));
    }

    @Operation(summary = "Delete a vote", description = "Deletes a vote by ID.")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return voteService.delete(id);
    }
}
