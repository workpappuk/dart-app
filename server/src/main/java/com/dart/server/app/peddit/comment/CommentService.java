package com.dart.server.app.peddit.comment;

import com.dart.server.app.peddit.comment.dto.CommentMapper;
import com.dart.server.app.peddit.comment.dto.CommentRequest;
import com.dart.server.app.peddit.comment.dto.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<CommentResponse> getAll() {
        return commentRepository.findAll().stream()
                .map(CommentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CommentResponse getById(UUID id) {
        return commentRepository.findById(id)
                .map(CommentMapper::toResponse)
                .orElse(null);
    }

    public CommentResponse create(CommentRequest request) {
        CommentEntity entity = CommentMapper.toEntity(request);
        return CommentMapper.toResponse(commentRepository.save(entity));
    }

    public CommentResponse update(UUID id, CommentRequest request) {
        return commentRepository.findById(id)
                .map(entity -> {
                    entity.setContent(request.getContent());
                    return CommentMapper.toResponse(commentRepository.save(entity));
                }).orElse(null);
    }

    public boolean delete(UUID id) {
        return commentRepository.findById(id).map(entity -> {
            entity.setMarkedForDeletion(true);
            commentRepository.save(entity);
            return true;
        }).orElse(false);
    }

    public CommentEntity getByIdEntity(UUID id) {
        return commentRepository.findById(id).orElse(null);
    }

    public CommentEntity save(CommentEntity entity) {
        return commentRepository.save(entity);
    }
}
