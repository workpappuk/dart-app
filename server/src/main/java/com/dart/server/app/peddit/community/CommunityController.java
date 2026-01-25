package com.dart.server.app.peddit.community;

import com.dart.server.app.peddit.community.dto.CommunityRequest;
import com.dart.server.app.peddit.community.dto.CommunityResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communities")
@Tag(name = "Communities", description = "Endpoints for managing communities")
public class CommunityController {
    @Autowired
    private CommunityService communityService;

    @GetMapping
    public List<CommunityResponse> getAll() {
        return communityService.getAll();
    }

    @GetMapping("/{id}")
    public CommunityResponse getById(@PathVariable Long id) {
        return communityService.getById(id);
    }

    @PostMapping
    public CommunityResponse create(@RequestBody CommunityRequest request) {
        return communityService.create(request);
    }

    @PutMapping("/{id}")
    public CommunityResponse update(@PathVariable Long id, @RequestBody CommunityRequest request) {
        return communityService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return communityService.delete(id);
    }
}

