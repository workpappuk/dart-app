package com.dart.server.app.peddit.vote;

import com.dart.server.app.peddit.vote.dto.VoteMapper;
import com.dart.server.app.peddit.vote.dto.VoteRequest;
import com.dart.server.app.peddit.vote.dto.VoteResponse;
import com.dart.server.common.response.DartApiResponse;
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
    public DartApiResponse<List<VoteResponse>> getAll() {
        return DartApiResponse.<List<VoteResponse>>builder()
                .success(true)
                .message("OK")
                .data(voteService.getAll().stream().map(VoteMapper::toResponse).toList())
                .build();
    }

    @Operation(summary = "Get vote by ID", description = "Returns a single vote by its ID.")
    @GetMapping("/{id}")
    public DartApiResponse<VoteResponse> getById(@PathVariable Long id) {
        var entity = voteService.getById(id);
        if (entity == null)
            return DartApiResponse.<VoteResponse>builder().success(false).message("Vote not found").build();
        return DartApiResponse.<VoteResponse>builder().success(true).message("OK").data(VoteMapper.toResponse(entity)).build();
    }

    @Operation(summary = "Create a new vote", description = "Creates a new vote.")
    @PostMapping
    public DartApiResponse<VoteResponse> create(@RequestBody VoteRequest request) {
        var entity = voteService.create(VoteMapper.toEntity(request));
        return DartApiResponse.<VoteResponse>builder().success(true).message("Created").data(VoteMapper.toResponse(entity)).build();
    }

    @Operation(summary = "Delete a vote", description = "Marks a vote as deleted by ID.")
    @DeleteMapping("/{id}")
    public DartApiResponse<Boolean> delete(@PathVariable Long id) {
        var entity = voteService.getById(id);
        if (entity == null) return DartApiResponse.<Boolean>builder().success(false).message("Vote not found").build();
        if (entity.isMarkedForDeletion()) {
            return DartApiResponse.<Boolean>builder().success(false).message("Vote already marked for deletion").build();
        }
        entity.setMarkedForDeletion(true);
        voteService.save(entity);
        return DartApiResponse.<Boolean>builder().success(true).message("Marked for deletion").data(true).build();
    }
}
