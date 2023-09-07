package com.demojava.redditdemo.map;

import com.demojava.redditdemo.dto.SubredditRequestDto;
import com.demojava.redditdemo.dto.SubredditDto;
import com.demojava.redditdemo.model.Subreddit;
import java.time.Instant;
import java.util.ArrayList;

public class SubredditMap {
    public static Subreddit mapSubredditDto(SubredditRequestDto subredditRequestDto) {

        return Subreddit.builder()
                .name(subredditRequestDto.getName())
                .description(subredditRequestDto.getDescription())
                .style(subredditRequestDto.getStyle())
                .NSFW(subredditRequestDto.getNSFW())
                .createDate(Instant.now())
                .title(subredditRequestDto.getTitle())
                .postsId(new ArrayList<>())
                .usersSubscribed(0)
                .build();
    }
    public static SubredditDto mapSubredditToPostResponse(Subreddit subreddit) {

        return SubredditDto.builder()
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .title(subreddit.getTitle())
                .createdDate(subreddit.getCreateDate())
                .build();

    }
}
