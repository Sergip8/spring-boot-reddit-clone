package com.demojava.redditdemo.service;


import com.demojava.redditdemo.config.JwtService;
import com.demojava.redditdemo.dto.SubredditRequestDto;
import com.demojava.redditdemo.dto.SubredditDto;
import com.demojava.redditdemo.exception.SpringRedditException;
import com.demojava.redditdemo.map.SubredditMap;
import com.demojava.redditdemo.model.Subreddit;
import com.demojava.redditdemo.repository.PostRepository;
import com.demojava.redditdemo.repository.SubredditRepository;
import com.demojava.redditdemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditService {


    private final SubredditRepository subredditRepository;
    private final PostRepository postRepository;

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Transactional
    public String save(SubredditRequestDto subredditRequestDto){
       Subreddit subreddit = SubredditMap.mapSubredditDto(subredditRequestDto);

        var user = userRepository.findByUsername(jwtService.getCurrentUser()).orElseThrow(() -> new SpringRedditException(""));
       subreddit.setUser(user);
       subredditRepository.save(subreddit);

       return subreddit.getId();
    }

    @Transactional
    public List<SubredditRequestDto> getAll() {
       return subredditRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private SubredditRequestDto mapToDto(Subreddit subreddit) {
        return SubredditRequestDto.builder()

                .name(subreddit.getName())
                .description(subreddit.getDescription())

                .build();
}
    public SubredditRequestDto getSubreddit(String id) {
        var subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No existe el Subreddit"));
        return mapToDto(subreddit);
    }

    public List<String> findSubreddit(String name) {
        var subreddits = subredditRepository.findByNameContaining( name).orElseThrow(() -> new SpringRedditException("No hay subreddits"));
        List<String> subredditFinds = subreddits.stream().map(Subreddit::getName).toList();

        return subredditFinds;
    }

    private SubredditDto mapSubredditToPostResponse(Subreddit subreddit) {

        return SubredditDto.builder()
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .title(subreddit.getTitle())
                .createdDate(subreddit.getCreateDate())
                .style(subreddit.getStyle())
                .usersSubscribed(subreddit.getUsersSubscribed())
                .build();

    }
    public static String getDuration(Instant createdDate) {
        Duration duration = Duration.between(createdDate, Instant.now());
        if(duration.toMinutes() < 1)
            return "creado hace un instante";
        else if(duration.toMinutes() < 60)
        return "creado hace "+ duration.toMinutes() + " minutos";
        else if(duration.toHours() >= 1 && duration.toHours() < 24)
            return "creado hace "+ duration.toHours()+ " Horas";
        else if (duration.toDays() >= 1)
            return "creado hace "+ duration.toDays()+ " Dias";
        return "";
    }

    public SubredditDto getSubredditByName(String name) {
        Subreddit subreddit = subredditRepository.findByName(name).orElseThrow(() -> new SpringRedditException("No hay subreddit"));;
        return mapSubredditToPostResponse(subreddit);
    }
}
