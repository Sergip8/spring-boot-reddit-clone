package com.demojava.redditdemo.model;

import com.demojava.redditdemo.exception.SpringRedditException;

import java.util.Arrays;
import java.util.Objects;

public enum VoteType {

    UPVOTE(0),
    NOVOTE(1),
    DOWNVOTE(2),
    ;
    public int direction;
   VoteType(int direccion){

    }
    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> Objects.equals(value.getDirection(), direction))
                .findAny()
                .orElseThrow(() -> new SpringRedditException("Vote not found"));
    }
    public Integer getDirection() {
        return direction;
    }
}
