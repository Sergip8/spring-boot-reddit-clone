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
public class SubredditDto {
    private String name;
    private String description;
    private Instant createdDate;
    private String title;
    private Integer usersSubscribed;
    private String style;







}
