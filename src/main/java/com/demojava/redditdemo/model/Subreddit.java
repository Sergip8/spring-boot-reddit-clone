package com.demojava.redditdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

import java.util.List;
import java.util.Set;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("subreddit")

public class Subreddit {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String title;
    private String description;
    @ElementCollection
    private List<String> postsId;

    private Integer usersSubscribed;

    private String style;
    private Boolean NSFW; 
    private Instant createDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}