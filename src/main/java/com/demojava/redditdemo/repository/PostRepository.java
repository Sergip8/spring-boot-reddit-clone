package com.demojava.redditdemo.repository;

import com.demojava.redditdemo.dto.PostResponse;
import com.demojava.redditdemo.model.Post;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {







  /*  @Query("SELECT new com.demojava.redditdemo.dto.PostResponse(" +
            "p.id, p.Name, p.url, p.description, p.user.username, p.subreddit.name, size(p.comments) , p.createdDate )" +
            " from Post p where p.id =:postId ")
    Optional<PostResponse> getPostResponseById(@Param("postId") Long postId);
    @Query("SELECT new com.demojava.redditdemo.dto.PostResponse(" +
            "p.id, p.Name, p.url, p.description, p.user.username, p.subreddit.name, size(p.comments) , p.createdDate )" +
            " from Post p")
    Optional<List<PostResponse>> getAllPostResponse();

    @Query("SELECT new com.demojava.redditdemo.dto.PostResponse(" +
            "p.id, p.Name, p.url, p.description, p.user.username, p.subreddit.name, size(p.comments) , p.createdDate )" +
            " from Post p where p.subreddit.name =:name ")
    Optional<List<PostResponse>> getPostResponseBySubredditName(@Param("name") String name);
    Optional<List<Post>> findAllBySubredditName(String name);*/

    Optional<List<Post>> findAllByAuthorUsername(String user);


    List<Post> findAllBySubredditName(String name);
}
