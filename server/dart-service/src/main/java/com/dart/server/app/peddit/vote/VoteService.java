package com.dart.server.app.peddit.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;

    public List<VoteEntity> getAll() {
        return voteRepository.findAll();
    }

    public VoteEntity getById(UUID id) {
        return voteRepository.findById(id).orElse(null);
    }

    public VoteEntity create(VoteEntity vote) {
        return voteRepository.save(vote);
    }

    public VoteEntity save(VoteEntity entity) {
        return voteRepository.save(entity);
    }


}
