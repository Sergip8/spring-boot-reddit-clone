package com.demojava.redditdemo.controller;

import com.demojava.redditdemo.dto.PostRequest;
import com.demojava.redditdemo.dto.PostResponse;
import com.demojava.redditdemo.model.Post;
import com.demojava.redditdemo.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
@PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest){
    String id = postService.savePost(postRequest);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(id).toUri();
    return ResponseEntity.created(location).build();
}

@GetMapping("/{id}")
 public ResponseEntity<PostResponse> getPostById(@PathVariable String id){

    PostResponse postResponse = postService.getPostAndComments(id);
    return  ResponseEntity.ok(postResponse);
}
@GetMapping
    public ResponseEntity<List<PostResponse>> getAllPost() {
    List<PostResponse> postResponseList = postService.getAllPost();
    System.out.println(postResponseList);
    return ResponseEntity.ok(postResponseList);
}
    @GetMapping("/user/{user}")
    public ResponseEntity<List<PostResponse>> getPostByUser(@PathVariable String user){
        List<PostResponse> postResponseList = postService.getAllPostByUserName(user);
        return ResponseEntity.ok(postResponseList);
}
    @GetMapping("/subreddit/{name}")
    public ResponseEntity<List<PostResponse>> getAllPostOfSubreddit(@PathVariable String name){
        List<PostResponse> postResponses = postService.getAllPostBySubreddit(name);
        System.out.println(postResponses);
        return ResponseEntity.ok(postResponses);
    }
}
