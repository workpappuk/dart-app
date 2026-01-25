package com.dart.server.app.peddit.comment;

import com.dart.server.app.peddit.comment.dto.CommentMapper;
import com.dart.server.app.peddit.comment.dto.CommentRequest;
import com.dart.server.app.peddit.comment.dto.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<CommentResponse> getAll() {
        return commentRepository.findAll().stream()
                .map(CommentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CommentResponse getById(Long id) {
        return commentRepository.findById(id)
                .map(CommentMapper::toResponse)
                .orElse(null);
    }

    public CommentResponse create(CommentRequest request) {
        CommentEntity entity = CommentMapper.toEntity(request);
        return CommentMapper.toResponse(commentRepository.save(entity));
    }

    public CommentResponse update(Long id, CommentRequest request) {
        return commentRepository.findById(id)
                .map(entity -> {
                    entity.setContent(request.getContent());
                    return CommentMapper.toResponse(commentRepository.save(entity));
                }).orElse(null);
    }

    public boolean delete(Long id) {
        return commentRepository.findById(id).map(entity -> {
            entity.setMarkedForDeletion(true);
            commentRepository.save(entity);
            return true;
        }).orElse(false);
    }
}

