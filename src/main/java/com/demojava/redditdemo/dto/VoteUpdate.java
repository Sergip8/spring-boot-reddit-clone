package com.demojava.redditdemo.dto;

import com.demojava.redditdemo.model.enums.VoteClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteUpdate {
   private String entityId;
    private String id;
    private int voteType;
    private VoteClass voteClass;

}
