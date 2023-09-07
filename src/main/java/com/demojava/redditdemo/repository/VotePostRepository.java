package com.demojava.redditdemo.repository;

import com.demojava.redditdemo.model.VotePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotePostRepository extends MongoRepository<VotePost, String> {
    Optional<VotePost> findTopByPostIdAndUserNameOrderByIdDesc(String postId, String currentUser);

    Optional<List<VotePost>> findAllByPostId(String id);

    List<VotePost> findAllByUserName(String userName);


}
