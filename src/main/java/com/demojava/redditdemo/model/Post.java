package com.demojava.redditdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("post")

public class Post {

    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String Name;
    private String url;
    @Lob
    @Column(length = 4000)
    private String description;
    private Integer voteCount;
    private String authorUsername;
    private Instant createdDate;

    private String subredditName;




}
