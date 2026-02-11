package com.dart.server.app.peddit.post;

import com.dart.server.app.peddit.community.CommunityEntity;
import com.dart.server.app.peddit.community.CommunityRepository;
import com.dart.server.app.peddit.post.dto.PostRequest;
import com.dart.server.app.peddit.post.dto.PostResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommunityRepository communityRepository;
    @InjectMocks
    private PostService postService;

    public PostServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        PostEntity post = new PostEntity();
        UUID id = UUID.randomUUID();
        post.setId(id);
        when(postRepository.findById(id)).thenReturn(Optional.of(post));
        PostEntity result = postService.getByIdEntity(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(postRepository.findById(id)).thenReturn(Optional.empty());
        PostEntity result = postService.getByIdEntity(id);
        assertNull(result);
    }

    // @Test
    // void testGetAll() {
    //     when(postRepository.findAll()).thenReturn(Collections.emptyList());
    //     assertTrue(postService.getAll().isEmpty());
    // }

    @Test
    void testCreateWithValidCommunity() {
        PostRequest req = new PostRequest();
        UUID cid = UUID.randomUUID();
        req.setCommunityId(cid);
        req.setTitle("title");
        req.setContent("content");
        CommunityEntity community = new CommunityEntity();
        community.setId(cid);
        when(communityRepository.findById(cid)).thenReturn(Optional.of(community));
        when(postRepository.save(any())).thenReturn(new PostEntity());
        PostResponse resp = postService.create(req);
        assertNotNull(resp);
    }

    @Test
    void testCreateWithInvalidCommunity() {
        PostRequest req = new PostRequest();
        UUID cid = UUID.randomUUID();
        req.setCommunityId(cid);
        when(communityRepository.findById(cid)).thenReturn(Optional.empty());
        PostResponse resp = postService.create(req);
        assertNull(resp);
    }

    @Test
    void testCreateWithNullCommunityId() {
        PostRequest req = new PostRequest();
        req.setCommunityId(null);
        assertNull(postService.create(req));
    }

    @Test
    void testCreateThrowsException() {
        PostRequest req = new PostRequest();
        UUID cid = UUID.randomUUID();
        req.setCommunityId(cid);
        when(communityRepository.findById(cid)).thenThrow(new RuntimeException("fail"));
        assertThrows(RuntimeException.class, () -> postService.create(req));
    }

    @Test
    void testUpdateFound() {
        UUID id = UUID.randomUUID();
        PostRequest req = new PostRequest();
        req.setTitle("newTitle");
        req.setContent("newContent");
        PostEntity entity = new PostEntity();
        entity.setId(id);
        when(postRepository.findById(id)).thenReturn(Optional.of(entity));
        when(postRepository.save(any())).thenReturn(entity);
        PostResponse resp = postService.update(id, req);
        assertEquals("newTitle", resp.getTitle());
        assertEquals("newContent", resp.getContent());
    }

    @Test
    void testUpdateNotFound() {
        UUID id = UUID.randomUUID();
        PostRequest req = new PostRequest();
        when(postRepository.findById(id)).thenReturn(Optional.empty());
        PostResponse resp = postService.update(id, req);
        assertNull(resp);
    }

    @Test
    void testUpdateWithNullRequest() {
        UUID id = UUID.randomUUID();
        assertNull(postService.update(id, null));
    }

    @Test
    void testUpdateThrowsException() {
        UUID id = UUID.randomUUID();
        PostRequest req = new PostRequest();
        when(postRepository.findById(id)).thenThrow(new RuntimeException("fail"));
        assertThrows(RuntimeException.class, () -> postService.update(id, req));
    }

    @Test
    void testDeleteFound() {
        UUID id = UUID.randomUUID();
        PostEntity entity = new PostEntity();
        entity.setId(id);
        when(postRepository.findById(id)).thenReturn(Optional.of(entity));
        when(postRepository.save(any())).thenReturn(entity);
        assertTrue(postService.delete(id));
    }

    @Test
    void testDeleteNotFound() {
        UUID id = UUID.randomUUID();
        when(postRepository.findById(id)).thenReturn(Optional.empty());
        assertFalse(postService.delete(id));
    }

    @Test
    void testDeleteThrowsException() {
        UUID id = UUID.randomUUID();
        when(postRepository.findById(id)).thenThrow(new RuntimeException("fail"));
        assertThrows(RuntimeException.class, () -> postService.delete(id));
    }

    @Test
    void testSave() {
        PostEntity entity = new PostEntity();
        when(postRepository.save(entity)).thenReturn(entity);
        assertEquals(entity, postService.save(entity));
    }

    @Test
    void testSaveNullEntity() {
        when(postRepository.save((PostEntity) isNull())).thenReturn(null);
        assertNull(postService.save(null));
    }
}
