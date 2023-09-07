package com.demojava.redditdemo.service;

import com.demojava.redditdemo.config.JwtService;
import com.demojava.redditdemo.dto.CommentPost;
import com.demojava.redditdemo.dto.CommentReply;
import com.demojava.redditdemo.dto.CommentResponse;
import com.demojava.redditdemo.dto.CommentUserDto;
import com.demojava.redditdemo.exception.SpringRedditException;
import com.demojava.redditdemo.model.Comment;
import com.demojava.redditdemo.model.Post;
import com.demojava.redditdemo.model.User;
import com.demojava.redditdemo.repository.CommentRepository;
import com.demojava.redditdemo.repository.PostRepository;
import com.demojava.redditdemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtService jwtService;

   /* public Long createComment(CommentPost commentPost) {
        var comment = mapToComment(commentPost);
        commentRepository.save(comment);
        return comment.getId();
    }*/
    public Comment createComment(CommentReply commentReply) {
        Comment comment;
        if(commentReply.getCommentReplyId() == null){
            comment = mapToComment(commentReply);

        }
        else{

            comment = mapToCommentReply(commentReply);
        }

        //var post = postRepository.findById(commentReply.getPostId()).orElseThrow(() -> new SpringRedditException("no post"));

        Comment comm =  commentRepository.save(comment);



        return comm;
    }

    private User getUser(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(() -> new SpringRedditException("usuario comment"));
    }
    private Post getPost(String postId) {
        return postRepository.findById(postId).orElseThrow(() -> new SpringRedditException("post no encontrado"));

    }

    private Comment mapToCommentReply(CommentReply commentReply) {

        //var comment = commentRepository.findById(commentReply.getCommentReplyId()).orElseThrow(() -> new SpringRedditException("comment no encontrado"));;
        return Comment.builder()

                .createdDate(Instant.now())
                .text(commentReply.getText())
                .authorUsername(jwtService.getCurrentUser())
                .postId(commentReply.getPostId())
                .position(commentReply.getPosition()+1)
                .replyId(commentReply.getCommentReplyId())
                .build();
    }

    private Comment mapToComment(CommentReply commentPost) {

        return Comment.builder()
                .createdDate(Instant.now())
                .text(commentPost.getText())
                .authorUsername(jwtService.getCurrentUser())
                .postId(commentPost.getPostId())
                .position(0)
                .build();
    }


    public List<CommentResponse> getCommentsOfPost(String id) {
        var comments = commentRepository.findAllByPostIdOrderByCreatedDate(id);
        System.out.println(orderComment(comments));
        return orderComment(comments);
    }
    private CommentResponse mapCommentToCommentResponse(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .userName(comment.getAuthorUsername())
                .text(comment.getText())
                .position(comment.getPosition())
                .reply(new ArrayList<>())
                .commentReplyId(comment.getReplyId())
                .duration(SubredditService.getDuration(comment.getCreatedDate()))
                .build();




    }
    private List<CommentResponse> orderComment(List<Comment> comments) {

        List<CommentResponse> result = new ArrayList<>();
       /* Integer position = 0;
        Integer index = 0;*/
        for (Comment comment : comments) {
            result.add(mapCommentToCommentResponse(comment));
         /*  if (comment.getPosition() > position)
                position = comment.getPosition();
        }

        while (position >=0){

            for (int i = 0; i<result.size()-index; i++) {
                for(int j = 0; j<result.size()-index; j++){
                    index =0;
                    if (Objects.equals(result.get(j).getCommentReplyId(), result.get(i).getId()) &&  result.get(i).getPosition() > position-2){
                        result.get(i).getReply().add(result.get(j));
                        result.remove(result.get(j));
                        index = 1;
                    }

                }

            }
            --position;
           // result.forEach(c -> result.stream().filter(cf -> Objects.equals(cf.getCommentReplyId(), c.getId())).forEach(cf -> c.getReply().add(cf)));

    */
        }
        //res = result.stream().distinct().toList();
        return result;

/*
        List<CommentResponse> result = new ArrayList<>();

        List<CommentResponse> pos0 = new ArrayList<>();

        List<CommentResponse> pos1 = new ArrayList<>();
        List<CommentResponse> pos2 = new ArrayList<>();
        List<CommentResponse> pos3 = new ArrayList<>();
        comments.stream().filter(c -> c.getPosition() == 0)
                .forEach(comment-> pos0.add(mapCommentToCommentResponse(comment)));

        comments.stream().filter(c -> c.getPosition() == 1)
                .forEach(comment-> pos1.add(mapCommentToCommentResponse(comment)));

        comments.stream().filter(c -> c.getPosition() == 2)
                .forEach(comment -> pos2.add(mapCommentToCommentResponse(comment)));

        comments.stream().filter(c -> c.getPosition() == 3)
                .forEach(comment -> pos3.add(mapCommentToCommentResponse(comment)));

        pos2.forEach(c-> pos3.stream()
                .filter(x -> Objects.equals(x.getCommentReplyId(), c.getId()))
                .forEach(x -> c.getReply().add(x)));
        pos1.forEach(c-> pos2.stream()
                .filter(x -> Objects.equals(x.getCommentReplyId(), c.getId()))
                .forEach(x -> c.getReply().add(x)));
        pos0.forEach(c-> pos1.stream()
                .filter(x -> Objects.equals(x.getCommentReplyId(), c.getId()))
                .forEach(x -> c.getReply().add(x)));*/
          /*  for (int i = 0; i < comments.size(); i++) {
                for (int j = 0; j < comments.size(); j++) {
                    if (comments.get(i).getPosition().equals(0)) {
                        result.add(mapCommentToCommentResponse(comments.get(i)));
                        comments.remove(comments.get(i));

                    }
                    if(Objects.equals(comments.get(j).getReplyId(), comments.get(i).getId())){
                        var ci = mapCommentToCommentResponse(comments.get(i));
                        var cj = mapCommentToCommentResponse(comments.get(j));
                        ci.getReply().add(cj);
                        result.get(0).getReply().add(ci);
                        comments.remove(comments.get(i));
                    }
                }
            }*/

        //return pos0;
    }

    public List<CommentUserDto> getCommentsOfUser(String user) {

        List<Comment> comments = commentRepository.findAllByAuthorUsername(user);
        return mapCommentToCommentUserDto(comments);
    }

    private List<CommentUserDto> mapCommentToCommentUserDto(List<Comment> comments){
        return comments.stream().map(comment -> CommentUserDto.builder()
                .text(comment.getText())
                .id(comment.getId())
                .duration(SubredditService.getDuration(comment.getCreatedDate()))
                .userName(comment.getAuthorUsername()).build()).toList();
    }
}