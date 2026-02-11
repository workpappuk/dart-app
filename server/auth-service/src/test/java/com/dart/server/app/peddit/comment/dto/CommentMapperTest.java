package com.dart.server.app.peddit.comment.dto;

import org.junit.jupiter.api.Test;
import com.dart.server.app.peddit.comment.CommentEntity;
import java.util.UUID;
import com.dart.server.app.peddit.EEntityTargetType;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {
    @Test
    void testToEntity() {
        CommentRequest req = new CommentRequest();
        req.setContent("comment");
        UUID tid = UUID.randomUUID();
        req.setTargetId(tid);
        req.setTargetType(EEntityTargetType.POST);
        CommentEntity entity = CommentMapper.toEntity(req);
        assertEquals("comment", entity.getContent());
        assertEquals(tid, entity.getTargetId());
        assertEquals(EEntityTargetType.POST, entity.getTargetType());
    }
    @Test
    void testToResponse() {
        CommentEntity entity = new CommentEntity();
        entity.setId(UUID.randomUUID());
        entity.setContent("comment");
        entity.setTargetId(UUID.randomUUID());
        entity.setTargetType(EEntityTargetType.POST);
        entity.setMarkedForDeletion(true);
        CommentResponse resp = CommentMapper.toResponse(entity);
        assertEquals(entity.getId(), resp.getId());
        assertEquals("comment", resp.getContent());
        assertEquals(entity.getTargetId(), resp.getTargetId());
        assertEquals(EEntityTargetType.POST, resp.getTargetType());
        assertTrue(resp.isMarkedForDeletion());
    }
}

