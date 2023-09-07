package com.demojava.redditdemo.service;

import com.demojava.redditdemo.config.JwtService;
import com.demojava.redditdemo.dto.VoteCreate;
import com.demojava.redditdemo.dto.VoteDto;

import com.demojava.redditdemo.dto.VoteUpdate;
import com.demojava.redditdemo.exception.SpringRedditException;


import com.demojava.redditdemo.model.Vote;
import com.demojava.redditdemo.model.enums.VoteClass;
import com.demojava.redditdemo.repository.PostRepository;
import com.demojava.redditdemo.repository.VoteCommentRepository;
import com.demojava.redditdemo.repository.VotePostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {
    private final PostRepository postRepository;
    private final VotePostRepository votePostRepository;
    private final VoteCommentRepository voteCommentRepository;
    private final JwtService jwtService;




    public VoteDto getVoteCommentCount(String id, VoteClass vclass) {
        List<Vote> votes = voteCommentRepository.findAllByCommentIdAndVoteClass(id, vclass)
                .orElseThrow(() -> new SpringRedditException("No hay votos"));
        VoteDto voteDto = new VoteDto();
        for (var vote:votes) {
            voteDto.setId(vote.getId());
            if(vote.getVoteType() == 1)
            voteDto.setCount(voteDto.getCount()+1);
            if(vote.getVoteType() == 0)
                voteDto.setCount(voteDto.getCount()-1);
            voteDto.setVoteType(vote.getVoteType());
            if(Objects.equals(vote.getUserName(), jwtService.getCurrentUser())){
                voteDto.setVoteByUser(true);

            }
        }
        return voteDto;

    }

    public void deleteCommentVote(String id) {
        voteCommentRepository.deleteById(id);
    }

    public void createVote(VoteCreate voteCreate) {
        Vote vote= Vote.builder()
                .voteType(voteCreate.getVoteType())
                .userName(jwtService.getCurrentUser())
                .commentId(voteCreate.getId())
              .voteClass(voteCreate.getVoteClass()).build();

        System.out.println(voteCommentRepository.save(vote));

    }

    public void updateVote(VoteUpdate voteUpdate) {
        Vote vote= Vote.builder()
                .id(voteUpdate.getId())
                .voteType(voteUpdate.getVoteType())
                .userName(jwtService.getCurrentUser())
                .commentId(voteUpdate.getEntityId())
                .voteClass(voteUpdate.getVoteClass()).build();

        voteCommentRepository.save(vote);
    }


    }
