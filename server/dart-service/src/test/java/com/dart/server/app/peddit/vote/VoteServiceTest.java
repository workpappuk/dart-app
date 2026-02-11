package com.dart.server.app.peddit.vote;

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

class VoteServiceTest {
    @Mock
    private VoteRepository voteRepository;
    @InjectMocks
    private VoteService voteService;

    public VoteServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        VoteEntity vote = new VoteEntity();
        UUID id = UUID.randomUUID();
        vote.setId(id);
        when(voteRepository.findById(id)).thenReturn(Optional.of(vote));
        VoteEntity result = voteService.getById(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(voteRepository.findById(id)).thenReturn(Optional.empty());
        VoteEntity result = voteService.getById(id);
        assertNull(result);
    }

    @Test
    void testCreate() {
        VoteEntity vote = new VoteEntity();
        when(voteRepository.save(vote)).thenReturn(vote);
        VoteEntity result = voteService.create(vote);
        assertNotNull(result);
        verify(voteRepository).save(vote);
    }

    @Test
    void testSave() {
        VoteEntity vote = new VoteEntity();
        when(voteRepository.save(vote)).thenReturn(vote);
        VoteEntity result = voteService.save(vote);
        assertNotNull(result);
        verify(voteRepository).save(vote);
    }

    @Test
    void testGetAll() {
        when(voteRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(voteService.getAll().isEmpty());
    }

    @Test
    void testGetAllWithVotes() {
        VoteEntity vote = new VoteEntity();
        when(voteRepository.findAll()).thenReturn(Collections.singletonList(vote));
        assertEquals(1, voteService.getAll().size());
    }

    @Test
    void testCreateNull() {
        when(voteRepository.save(null)).thenReturn(null);
        VoteEntity result = voteService.create(null);
        assertNull(result);
    }
}
