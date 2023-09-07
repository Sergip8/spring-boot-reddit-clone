package com.demojava.redditdemo.repository;

import com.demojava.redditdemo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

  List<Comment>  findAllByPostIdOrderByCreatedDate(String postId);

  Long countAllByPostId(String postId);
    List<Comment> findAllByAuthorUsername(String user);

}
