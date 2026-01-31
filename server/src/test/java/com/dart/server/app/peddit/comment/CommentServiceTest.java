package com.dart.server.app.peddit.comment;

import com.dart.server.app.peddit.comment.dto.CommentRequest;
import com.dart.server.app.peddit.comment.dto.CommentResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentService commentService;

    public CommentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        CommentEntity comment = new CommentEntity();
        UUID id = UUID.randomUUID();
        comment.setId(id);
        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));
        CommentEntity result = commentService.getByIdEntity(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(commentRepository.findById(id)).thenReturn(Optional.empty());
        CommentEntity result = commentService.getByIdEntity(id);
        assertNull(result);
    }

    // @Test
    // void testGetAll() {
    //     when(commentRepository.findAll()).thenReturn(Collections.emptyList());
    //     assertTrue(commentService.getAll().isEmpty());
    // }

    @Test
    void testCreate() {
        CommentRequest req = new CommentRequest();
        req.setContent("content");
        CommentEntity entity = new CommentEntity();
        entity.setContent("content");
        when(commentRepository.save(any())).thenReturn(entity);
        CommentResponse resp = commentService.create(req);
        assertEquals("content", resp.getContent());
    }

    @Test
    void testCreateWithNullRequest() {
        assertNull(commentService.create(null));
    }

    @Test
    void testUpdateFound() {
        UUID id = UUID.randomUUID();
        CommentRequest req = new CommentRequest();
        req.setContent("newContent");
        CommentEntity entity = new CommentEntity();
        entity.setId(id);
        when(commentRepository.findById(id)).thenReturn(Optional.of(entity));
        when(commentRepository.save(any())).thenReturn(entity);
        CommentResponse resp = commentService.update(id, req);
        assertEquals("newContent", resp.getContent());
    }

    @Test
    void testUpdateNotFound() {
        UUID id = UUID.randomUUID();
        CommentRequest req = new CommentRequest();
        when(commentRepository.findById(id)).thenReturn(Optional.empty());
        CommentResponse resp = commentService.update(id, req);
        assertNull(resp);
    }

    @Test
    void testUpdateWithNullRequest() {
        UUID id = UUID.randomUUID();
        assertNull(commentService.update(id, null));
    }

    @Test
    void testUpdateThrowsException() {
        UUID id = UUID.randomUUID();
        CommentRequest req = new CommentRequest();
        when(commentRepository.findById(id)).thenThrow(new RuntimeException("fail"));
        assertThrows(RuntimeException.class, () -> commentService.update(id, req));
    }

    @Test
    void testDeleteFound() {
        UUID id = UUID.randomUUID();
        CommentEntity entity = new CommentEntity();
        entity.setId(id);
        when(commentRepository.findById(id)).thenReturn(Optional.of(entity));
        when(commentRepository.save(any())).thenReturn(entity);
        assertTrue(commentService.delete(id));
    }

    @Test
    void testDeleteNotFound() {
        UUID id = UUID.randomUUID();
        when(commentRepository.findById(id)).thenReturn(Optional.empty());
        assertFalse(commentService.delete(id));
    }

    @Test
    void testDeleteThrowsException() {
        UUID id = UUID.randomUUID();
        when(commentRepository.findById(id)).thenThrow(new RuntimeException("fail"));
        assertThrows(RuntimeException.class, () -> commentService.delete(id));
    }

    @Test
    void testSave() {
        CommentEntity entity = new CommentEntity();
        when(commentRepository.save(entity)).thenReturn(entity);
        assertEquals(entity, commentService.save(entity));
    }

    @Test
    void testSaveNullEntity() {
        when(commentRepository.save((CommentEntity) isNull())).thenReturn(null);
        assertNull(commentService.save(null));
    }
}
