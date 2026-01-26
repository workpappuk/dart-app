package com.dart.server.app.peddit.vote.dto;

import com.dart.server.app.peddit.vote.VoteEntity;
import com.dart.server.app.auth.UserEntity;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VoteMapperTest {
    @Test
    void testToEntity() {
        VoteRequest req = new VoteRequest();
        UUID tid = UUID.randomUUID();
        req.setTargetId(tid);
        req.setTargetType("POST");
        req.setUpvote(true);
        VoteEntity entity = VoteMapper.toEntity(req);
        assertEquals(tid, entity.getTargetId());
        assertEquals("POST", entity.getTargetType());
        assertTrue(entity.isUpvote());
    }
    @Test
    void testToResponseWithUser() {
        VoteEntity entity = new VoteEntity();
        entity.setId(UUID.randomUUID());
        entity.setTargetId(UUID.randomUUID());
        entity.setTargetType("POST");
        UserEntity user = new UserEntity();
        UUID uid = UUID.randomUUID();
        user.setId(uid);
        entity.setUser(user);
        entity.setUpvote(true);
        VoteResponse resp = VoteMapper.toResponse(entity);
        assertEquals(entity.getId(), resp.getId());
        assertEquals(entity.getTargetId(), resp.getTargetId());
        assertEquals("POST", resp.getTargetType());
        assertEquals(uid, resp.getUserId());
        assertTrue(resp.isUpvote());
    }
    @Test
    void testToResponseWithNullUser() {
        VoteEntity entity = new VoteEntity();
        entity.setId(UUID.randomUUID());
        entity.setTargetId(UUID.randomUUID());
        entity.setTargetType("POST");
        entity.setUser(null);
        entity.setUpvote(false);
        VoteResponse resp = VoteMapper.toResponse(entity);
        assertNull(resp.getUserId());
        assertFalse(resp.isUpvote());
    }
    @Test
    void testToEntityWithNullRequest() {
        VoteEntity entity = VoteMapper.toEntity(null);
        assertNull(entity.getTargetId());
        assertNull(entity.getTargetType());
        assertFalse(entity.isUpvote());
    }

    @Test
    void testToResponseWithNullEntity() {
        VoteResponse resp = VoteMapper.toResponse(new VoteEntity());
        assertNull(resp.getId());
        assertNull(resp.getTargetId());
        assertNull(resp.getTargetType());
        assertNull(resp.getUserId());
        assertFalse(resp.isUpvote());
    }
}
