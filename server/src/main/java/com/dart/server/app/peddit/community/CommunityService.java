package com.dart.server.app.peddit.community;

import com.dart.server.app.auth.UserEntity;
import com.dart.server.app.auth.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

    private CommunityMapper.CommunityWithUser mapWithUsers(CommunityEntity entity) {
        return new CommunityMapper.CommunityWithUser(
                entity,
                safeFindUser(entity.getCreatedBy()),
                safeFindUser(entity.getUpdatedBy())
        );
    }

    private UserEntity safeFindUser(String uuidStr) {
        try {
            return uuidStr == null ? null : userRepository.findById(UUID.fromString(uuidStr)).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    public Page<CommunityResponse> searchCommunities(String q, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommunityEntity> entities;
        if (AuthUtils.isAdmin()) {
            entities = communityRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByUpdatedAtDesc(q, q, pageable);
        } else {
            entities = communityRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndMarkedForDeletionFalseOrderByUpdatedAtDesc(q, q, pageable);
        }
        return entities.map(entity -> CommunityMapper.toResponse(mapWithUsers(entity)));
    }

    public CommunityResponse getById(UUID id) {
        return communityRepository.findById(id)
                .map(this::mapWithUsers)
                .map(CommunityMapper::toResponse)
                .orElse(null);
    }

    public CommunityResponse create(CommunityRequest request) {
        CommunityEntity entity = CommunityMapper.toEntity(request);
        CommunityEntity saved = communityRepository.save(entity);
        return CommunityMapper.toResponse(mapWithUsers(saved));
    }

    public CommunityResponse update(UUID id, CommunityRequest request) {
        return communityRepository.findById(id)
                .map(entity -> {
                    entity.setName(request.getName());
                    entity.setDescription(request.getDescription());
                    CommunityEntity updated = communityRepository.save(entity);
                    return CommunityMapper.toResponse(mapWithUsers(updated));
                })
                .orElse(null);
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
