package com.demojava.redditdemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.DataTruncation;
import java.time.Instant;

@Getter @Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("verificationToken")

public class VerificationToken {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String token;

    private String userId;
    private Instant expiryDate;

}