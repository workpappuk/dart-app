package com.dart.server.app.peddit.post;

import com.dart.server.app.peddit.community.CommunityEntity;
import com.dart.server.app.peddit.community.CommunityRepository;
import com.dart.server.app.peddit.post.dto.PostRequest;
import com.dart.server.app.peddit.post.dto.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommunityRepository communityRepository;

    public List<PostResponse> getAll() {
        return postRepository.findAll().stream()
                .map(PostMapper::toResponse)
                .collect(Collectors.toList());
    }

    public PostResponse getById(Long id) {
        return postRepository.findById(id)
                .map(PostMapper::toResponse)
                .orElse(null);
    }

    public PostResponse create(PostRequest request) {
        CommunityEntity community = communityRepository.findById(request.getCommunityId()).orElse(null);
        if (community == null) return null;
        PostEntity entity = PostMapper.toEntity(request, community);
        return PostMapper.toResponse(postRepository.save(entity));
    }

    public PostResponse update(Long id, PostRequest request) {
        return postRepository.findById(id)
                .map(entity -> {
                    entity.setTitle(request.getTitle());
                    entity.setContent(request.getContent());
                    return PostMapper.toResponse(postRepository.save(entity));
                }).orElse(null);
    }

    public boolean delete(Long id) {
        return postRepository.findById(id).map(entity -> {
            entity.setMarkedForDeletion(true);
            postRepository.save(entity);
            return true;
        }).orElse(false);
    }
}

