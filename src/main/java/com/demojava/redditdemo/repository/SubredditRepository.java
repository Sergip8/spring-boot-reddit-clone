package com.demojava.redditdemo.repository;

import com.demojava.redditdemo.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubredditRepository extends MongoRepository<Subreddit, String> {
    Optional<List<Subreddit>> findByNameContaining(String name);

    Optional<Subreddit> findByName(String name);
}
