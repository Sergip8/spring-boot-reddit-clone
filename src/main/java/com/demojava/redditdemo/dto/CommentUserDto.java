package com.demojava.redditdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentUserDto {
    private String text;
    private String userName;
    private String duration;
    private String id;
}
