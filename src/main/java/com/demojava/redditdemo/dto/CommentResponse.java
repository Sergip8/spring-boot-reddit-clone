package com.demojava.redditdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private String text;
    private String userName;
    private String duration;
    private Integer position;
    private String id;
    private String commentReplyId;
    private List<CommentResponse> reply;
}
