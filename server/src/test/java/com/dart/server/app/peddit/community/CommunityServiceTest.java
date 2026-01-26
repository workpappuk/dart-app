package com.dart.server.app.peddit.community;

import com.dart.server.app.peddit.community.dto.CommunityRequest;
import com.dart.server.app.peddit.community.dto.CommunityResponse;
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

class CommunityServiceTest {
    @Mock
    private CommunityRepository communityRepository;
    @InjectMocks
    private CommunityService communityService;

    public CommunityServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        CommunityEntity community = new CommunityEntity();
        UUID id = UUID.randomUUID();
        community.setId(id);
        when(communityRepository.findById(id)).thenReturn(Optional.of(community));
        CommunityEntity result = communityService.getByIdEntity(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(communityRepository.findById(id)).thenReturn(Optional.empty());
        CommunityEntity result = communityService.getByIdEntity(id);
        assertNull(result);
    }

    @Test
    void testGetAll() {
        when(communityRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(communityService.getAll().isEmpty());
    }

    @Test
    void testCreate() {
        CommunityRequest req = new CommunityRequest();
        req.setName("name");
        req.setDescription("desc");
        CommunityEntity entity = new CommunityEntity();
        entity.setName("name");
        entity.setDescription("desc");
        when(communityRepository.save(any())).thenReturn(entity);
        CommunityResponse resp = communityService.create(req);
        assertEquals("name", resp.getName());
        assertEquals("desc", resp.getDescription());
    }

    @Test
    void testUpdateFound() {
        UUID id = UUID.randomUUID();
        CommunityRequest req = new CommunityRequest();
        req.setName("newName");
        req.setDescription("newDesc");
        CommunityEntity entity = new CommunityEntity();
        entity.setId(id);
        when(communityRepository.findById(id)).thenReturn(Optional.of(entity));
        when(communityRepository.save(any())).thenReturn(entity);
        CommunityResponse resp = communityService.update(id, req);
        assertEquals("newName", resp.getName());
        assertEquals("newDesc", resp.getDescription());
    }

    @Test
    void testUpdateNotFound() {
        UUID id = UUID.randomUUID();
        CommunityRequest req = new CommunityRequest();
        when(communityRepository.findById(id)).thenReturn(Optional.empty());
        CommunityResponse resp = communityService.update(id, req);
        assertNull(resp);
    }

    @Test
    void testDeleteFound() {
        UUID id = UUID.randomUUID();
        CommunityEntity entity = new CommunityEntity();
        entity.setId(id);
        when(communityRepository.findById(id)).thenReturn(Optional.of(entity));
        when(communityRepository.save(any())).thenReturn(entity);
        assertTrue(communityService.delete(id));
    }

    @Test
    void testDeleteNotFound() {
        UUID id = UUID.randomUUID();
        when(communityRepository.findById(id)).thenReturn(Optional.empty());
        assertFalse(communityService.delete(id));
    }

    @Test
    void testSave() {
        CommunityEntity entity = new CommunityEntity();
        when(communityRepository.save(entity)).thenReturn(entity);
        assertEquals(entity, communityService.save(entity));
    }
}
