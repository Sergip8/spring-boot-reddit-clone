package com.demojava.redditdemo.exception;

import org.springframework.mail.MailException;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String message, MailException e) {
        super(message, e);

    }
    public SpringRedditException(String message){
        super(message);
    }
}
