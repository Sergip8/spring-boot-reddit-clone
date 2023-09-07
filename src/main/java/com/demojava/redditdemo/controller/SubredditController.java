package com.demojava.redditdemo.controller;



import com.demojava.redditdemo.dto.SubredditRequestDto;
import com.demojava.redditdemo.dto.SubredditDto;
import com.demojava.redditdemo.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;

@RestController
@RequestMapping("api/v1/subreddit")
@AllArgsConstructor

public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditRequestDto>createSubreddit(@RequestBody SubredditRequestDto subredditRequestDto){
        String id = subredditService.save(subredditRequestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }
    @GetMapping
    public ResponseEntity<List<SubredditRequestDto>> getAllSubreddits(){
          var subreddits = subredditService.getAll();
          return ResponseEntity.ok(subreddits);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SubredditRequestDto> getSubredditById(@PathVariable String id){

        return ResponseEntity.ok(subredditService.getSubreddit(id));
    }
    @GetMapping("/search/{name}")
        public ResponseEntity<List<String>> findSubreddit(@PathVariable String name){

        var subredditList = subredditService.findSubreddit(name);
        if(!Objects.equals(name, "")){
            return  ResponseEntity.ok(subredditList);

        }
        return  ResponseEntity.ok(emptyList());
    }
/*    @GetMapping("/r/{id}")
    public ResponseEntity<Subreddit_PostResponse> SubredditWithPosts(@PathVariable Long id){
        var subredditPosts = subredditService.findSubredditWithPostById(id);
        return  ResponseEntity.ok(subredditPosts);
    }*/

    @GetMapping("/r/{name}")
    public ResponseEntity<SubredditDto> getSubredditByName(@PathVariable String name){

        return ResponseEntity.ok(subredditService.getSubredditByName(name));
    }
}
