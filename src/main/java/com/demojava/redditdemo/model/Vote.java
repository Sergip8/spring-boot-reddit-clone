package com.demojava.redditdemo.model;

import com.demojava.redditdemo.model.enums.VoteClass;
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
@Document("vote")

public class Vote{

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private VoteClass voteClass;
    private String commentId;
    private String userName;
    private int voteType;

}