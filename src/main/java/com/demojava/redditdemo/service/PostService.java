package com.demojava.redditdemo.service;

import com.demojava.redditdemo.config.JwtService;
import com.demojava.redditdemo.dto.PostRequest;
import com.demojava.redditdemo.dto.PostResponse;
import com.demojava.redditdemo.dto.CommentResponse;
import com.demojava.redditdemo.exception.SpringRedditException;
import com.demojava.redditdemo.model.Comment;
import com.demojava.redditdemo.model.Post;
import com.demojava.redditdemo.model.Subreddit;
import com.demojava.redditdemo.repository.CommentRepository;
import com.demojava.redditdemo.repository.PostRepository;
import com.demojava.redditdemo.repository.SubredditRepository;
import com.demojava.redditdemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.demojava.redditdemo.service.SubredditService.getDuration;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final SubredditRepository subredditRepository;
    private final JwtService jwtService;

    @Transactional
    public String savePost(PostRequest postRequest) {
        var subreddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(() -> new SpringRedditException("No encontro el subreddit actual"));

        var post =  mapToPost(postRequest);
        //post.getSubreddit().getPostsId().add(post.getId());
        postRepository.save(post);
        subreddit.getPostsId().add(post.getId());
    subredditRepository.save(subreddit);
        return post.getId();
        //subredditRepository.save(subreddit);

    }


    private Post mapToPost(PostRequest postRequest) {

        return Post.builder()
                .Name(postRequest.getPostName())
                .description(postRequest.getDescription())
                .authorUsername(jwtService.getCurrentUser())
                .subredditName(postRequest.getSubredditName())
                .createdDate(Instant.now())
                .build();
    }

    public PostResponse getPostAndComments(String id) {

        var post = postRepository.findById(id).orElseThrow(() -> new SpringRedditException("No Post"));

        return mapToPostResponse(post);
    }

    private PostResponse mapToPostResponse(Post post) {
        Long postComments = commentRepository.countAllByPostId(post.getId());
        return PostResponse.builder()
                .id(post.getId())
                .postName(post.getName())
                .userName(post.getAuthorUsername())
                .url(post.getUrl())
                .commentCount(postComments)
                .subredditName(post.getSubredditName())
                .description(post.getDescription())
                .duration(post.getCreatedDate())
                .postName(post.getName())

                .build();

    }


    public List<PostResponse> getAllPost() {


        List<Post> posts=  postRepository.findAll();

        return posts.stream().map(this::mapToPostResponse).toList();
    }

    public List<PostResponse> getAllPostByUserName(String user) {
        List<Post> posts = postRepository.findAllByAuthorUsername(user).orElseThrow(() -> new SpringRedditException("No encontro el post actual"));

        return posts.stream().map(this::mapToPostResponse).toList();
    }

    public List<PostResponse> getAllPostBySubreddit(String name) {
        List<Post> post = postRepository.findAllBySubredditName(name);

        return post.stream().map(this::mapToPostResponse).toList();

    }

}
