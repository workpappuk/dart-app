package com.dart.server.app.peddit.community;

import com.dart.server.app.peddit.community.dto.CommunityMapper;
import com.dart.server.app.peddit.community.dto.CommunityRequest;
import com.dart.server.app.peddit.community.dto.CommunityResponse;
import com.dart.server.app.peddit.post.dto.PostMapper;
import com.dart.server.app.peddit.post.dto.PostResponse;
import com.dart.server.common.utils.AuthUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    public Page<CommunityResponse> searchCommunities(String q, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (AuthUtils.isAdmin()) {
            return communityRepository.findByDescriptionContainingIgnoreCaseAndMarkedForDeletionFalse(q, pageable)
                    .map(CommunityMapper::toResponse);
        } else {
            return communityRepository.findByDescriptionContainingIgnoreCase(q, pageable)
                    .map(CommunityMapper::toResponse);
        }
    }

    public CommunityResponse getById(UUID id) {
        return communityRepository.findById(id)
                .map(CommunityMapper::toResponse)
                .orElse(null);
    }

    public CommunityResponse create(CommunityRequest request) {
        CommunityEntity entity = CommunityMapper.toEntity(request);
        return CommunityMapper.toResponse(communityRepository.save(entity));
    }

    public CommunityResponse update(UUID id, CommunityRequest request) {
        return communityRepository.findById(id)
                .map(entity -> {
                    entity.setName(request.getName());
                    entity.setDescription(request.getDescription());
                    return CommunityMapper.toResponse(communityRepository.save(entity));
                }).orElse(null);
    }

    public boolean delete(UUID id) {
        return communityRepository.findById(id).map(entity -> {
            entity.setMarkedForDeletion(true);
            communityRepository.save(entity);
            return true;
        }).orElse(false);
    }

    public CommunityEntity getByIdEntity(UUID id) {
        return communityRepository.findById(id).orElse(null);
    }

    public CommunityEntity save(CommunityEntity entity) {
        return communityRepository.save(entity);
    }
}
