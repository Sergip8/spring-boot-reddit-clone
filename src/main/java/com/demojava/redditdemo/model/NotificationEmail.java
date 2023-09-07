package com.demojava.redditdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class NotificationEmail {
    private String subject;
    private String recipient;
    private String body;
}