package com.demojava.redditdemo.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private String id;
    private String postName;
    private String url;
    private String description;
    private String userName;
    private String subredditName;
    private  Long commentCount;
    private Instant duration;
    //private List<CommentResponse> comments;
}