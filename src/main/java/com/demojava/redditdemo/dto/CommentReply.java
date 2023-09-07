package com.demojava.redditdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReply {

    private String postId;

    private String authorUsername;
    private String text;
    private Integer position;
    private String commentReplyId;
}
