package com.demojava.redditdemo.controller;

import com.demojava.redditdemo.dto.CommentPost;
import com.demojava.redditdemo.dto.CommentReply;
import com.demojava.redditdemo.dto.CommentResponse;
import com.demojava.redditdemo.dto.CommentUserDto;
import com.demojava.redditdemo.model.Comment;
import com.demojava.redditdemo.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/v1/comment")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;
/*    @PostMapping("/post")
    public ResponseEntity commentPost(@RequestBody CommentPost commentPost){
       Long id = commentService.createComment(commentPost);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();


    }*/
    @PostMapping("/reply")
    public Comment replyComment(@RequestBody CommentReply commentReply){
        return commentService.createComment(commentReply);

    }
    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentResponse>> commentsOfPost(@PathVariable String id){
        List<CommentResponse> commentResponses = commentService.getCommentsOfPost(id);
        return ResponseEntity.ok(commentResponses);
    }
    @GetMapping("/{user}")
    public ResponseEntity<List<CommentUserDto>> commentsUser(@PathVariable String user){
        List<CommentUserDto> commentUser = commentService.getCommentsOfUser(user);
        return ResponseEntity.ok(commentUser);
    }


}
