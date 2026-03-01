package com.dart.server.app.peddit.post.dto;

import com.dart.server.app.peddit.community.CommunityEntity;
import com.dart.server.app.peddit.post.PostEntity;
import com.dart.server.app.auth.UserEntity;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PostMapperTest {
    @Test
    void testToEntity() {
        PostRequest req = new PostRequest();
        req.setTitle("title");
        req.setContent("content");
        CommunityEntity community = new CommunityEntity();
        PostEntity entity = PostMapper.toEntity(req, community);
        assertEquals("title", entity.getTitle());
        assertEquals("content", entity.getContent());
        assertEquals(community, entity.getCommunity());
    }
    @Test
    void testToResponseWithAuthor() {
        PostEntity entity = new PostEntity();
        entity.setId(UUID.randomUUID());
        entity.setTitle("title");
        entity.setContent("content");
        CommunityEntity community = new CommunityEntity();
        community.setId(UUID.randomUUID());
        entity.setCommunity(community);
        UserEntity author = new UserEntity();
        UUID aid = UUID.randomUUID();
        author.setId(aid);
        entity.setAuthor(author);
        entity.setMarkedForDeletion(true);
        PostResponse resp = PostMapper.toResponse(entity);
        assertEquals(entity.getId(), resp.getId());
        assertEquals("title", resp.getTitle());
        assertEquals("content", resp.getContent());
        assertEquals(community.getId(), resp.getCommunityId());
        assertEquals(aid, resp.getAuthorId());
        assertTrue(resp.isMarkedForDeletion());
    }
    @Test
    void testToResponseWithNullAuthor() {
        PostEntity entity = new PostEntity();
        entity.setId(UUID.randomUUID());
        entity.setTitle("title");
        entity.setContent("content");
        CommunityEntity community = new CommunityEntity();
        community.setId(UUID.randomUUID());
        entity.setCommunity(community);
        entity.setAuthor(null);
        entity.setMarkedForDeletion(false);
        PostResponse resp = PostMapper.toResponse(entity);
        assertNull(resp.getAuthorId());
        assertFalse(resp.isMarkedForDeletion());
    }
    @Test
    void testToEntityWithNullRequest() {
        CommunityEntity community = new CommunityEntity();
        PostEntity entity = PostMapper.toEntity(null, community);
        assertNull(entity.getTitle());
        assertNull(entity.getContent());
        assertEquals(community, entity.getCommunity());
    }

    @Test
    void testToEntityWithNullCommunity() {
        PostRequest req = new PostRequest();
        req.setTitle("title");
        req.setContent("content");
        PostEntity entity = PostMapper.toEntity(req, null);
        assertEquals("title", entity.getTitle());
        assertEquals("content", entity.getContent());
        assertNull(entity.getCommunity());
    }

    @Test
    void testToResponseWithNullEntity() {
        PostResponse resp = PostMapper.toResponse(new PostEntity());
        assertNull(resp.getId());
        assertNull(resp.getTitle());
        assertNull(resp.getContent());
        assertNull(resp.getCommunityId());
        assertNull(resp.getAuthorId());
        assertFalse(resp.isMarkedForDeletion());
    }
}
