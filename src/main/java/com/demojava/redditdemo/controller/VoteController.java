package com.demojava.redditdemo.controller;

import com.demojava.redditdemo.dto.VoteCreate;
import com.demojava.redditdemo.dto.VoteDto;

import com.demojava.redditdemo.dto.VoteUpdate;
import com.demojava.redditdemo.model.enums.VoteClass;
import com.demojava.redditdemo.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/votes")
public class VoteController {

    private VoteService voteService;
    @PostMapping("/comment")
    public void voteComment(@RequestBody VoteCreate voteCreate){
        System.out.println(voteCreate);
        voteService.createVote(voteCreate);
    }
    @PutMapping("/comment")
    public void updateVote(@RequestBody VoteUpdate voteUpdate){
        System.out.println(voteUpdate);

        voteService.updateVote(voteUpdate);

    }
    @GetMapping("/comment/{id}/{vclass}")
    public  ResponseEntity<VoteDto> getVotesComment(@PathVariable String id,@PathVariable VoteClass vclass){
        return ResponseEntity.ok(voteService.getVoteCommentCount(id,  vclass));
    }
    @DeleteMapping("/comment/{id}")
    public  void deleteCommentVote(@PathVariable String id){
        voteService.deleteCommentVote(id);

    }

}
