package com.demojava.redditdemo.repository;



import com.demojava.redditdemo.model.Vote;
import com.demojava.redditdemo.model.enums.VoteClass;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface VoteCommentRepository extends MongoRepository<Vote, String> {

    Optional<Vote> findTopByCommentIdAndUserNameOrderByIdDesc(String commentId, String userName);

    Optional<List<Vote>> findAllByCommentIdAndVoteClass(String id, VoteClass voteClass);

    List<Vote> findAllByUserName(String userName);

}
