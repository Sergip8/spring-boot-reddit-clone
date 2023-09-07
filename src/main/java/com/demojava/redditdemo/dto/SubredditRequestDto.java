package com.demojava.redditdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubredditRequestDto {
    private String name;
    private String title;
    private String description;
    private String style;
    private Boolean NSFW;

}