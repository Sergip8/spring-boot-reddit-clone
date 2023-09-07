package com.demojava.redditdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("votePost")

public class VotePost {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private Long postId;

    private String userName;
    private Integer voteType;

}