package com.demojava.redditdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    private String id;
    private int voteType;
    private int count = 0;
    private boolean isVoteByUser = false;
}
