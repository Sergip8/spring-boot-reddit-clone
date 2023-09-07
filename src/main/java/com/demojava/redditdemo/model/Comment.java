package com.demojava.redditdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("comment")
public class Comment {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Integer position;
    private String authorUsername;
    private String postId;
    private String text;
    private Instant createdDate;
    private Integer voteCount;
    private String replyId;


}
