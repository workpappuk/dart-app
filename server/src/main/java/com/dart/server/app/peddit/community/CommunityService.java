package com.dart.server.app.peddit.community;

import com.dart.server.app.peddit.community.dto.CommunityMapper;
import com.dart.server.app.peddit.community.dto.CommunityRequest;
import com.dart.server.app.peddit.community.dto.CommunityResponse;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    public List<CommunityResponse> getAll() {
        return communityRepository.findAll().stream()
                .map(CommunityMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CommunityResponse getById(Long id) {
        return communityRepository.findById(id)
                .map(CommunityMapper::toResponse)
                .orElse(null);
    }

    public CommunityResponse create(CommunityRequest request) {
        CommunityEntity entity = CommunityMapper.toEntity(request);
        return CommunityMapper.toResponse(communityRepository.save(entity));
    }

    public CommunityResponse update(Long id, CommunityRequest request) {
        return communityRepository.findById(id)
                .map(entity -> {
                    entity.setName(request.getName());
                    entity.setDescription(request.getDescription());
                    return CommunityMapper.toResponse(communityRepository.save(entity));
                }).orElse(null);
    }

    public boolean delete(Long id) {
        return communityRepository.findById(id).map(entity -> {
            entity.setMarkedForDeletion(true);
            communityRepository.save(entity);
            return true;
        }).orElse(false);
    }
}

