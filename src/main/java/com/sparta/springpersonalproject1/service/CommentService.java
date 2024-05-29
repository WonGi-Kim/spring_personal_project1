package com.sparta.springpersonalproject1.service;

import com.sparta.springpersonalproject1.dto.commentDto.CommentRequestDto;
import com.sparta.springpersonalproject1.dto.commentDto.CommentResponseDto;
import com.sparta.springpersonalproject1.entity.Comment;
import com.sparta.springpersonalproject1.entity.ToDoList;
import com.sparta.springpersonalproject1.entity.User;
import com.sparta.springpersonalproject1.repository.CommentRepository;
import com.sparta.springpersonalproject1.repository.ToDoListRepository;
import com.sparta.springpersonalproject1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ToDoListRepository toDoListRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ToDoListRepository toDoListRepository, UserRepository userRepository) {
        this.toDoListRepository = toDoListRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public CommentResponseDto addComment(CommentRequestDto commentRequestDto, String username) {
        ToDoList toDoList = findToDoListById(commentRequestDto.getToDoListId());
        User user = findUserByUsername(username);

        // 데이터 유효성 검사
        if (commentRequestDto.getCommentContent() == null || commentRequestDto.getCommentContent().isEmpty()) {
            throw new IllegalArgumentException("Invalid commentDto Content");
        }

        Comment comment = new Comment(commentRequestDto, toDoList, user);
        user.getComments().add(comment);
        toDoList.getComments().add(comment);
        comment.setCommentDate(new Timestamp(System.currentTimeMillis()));
        comment.setUsername(username);
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment);
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, String username) {
        User user = findUserByUsername(username);
        Comment comment = findByCommentId(id);

        if (comment.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("Comment does not belong to user");
        }
        if (comment.getCommentContent() == null || comment.getCommentContent().isEmpty()) {
            throw new IllegalArgumentException("Comment Content cannot be empty");
        }

        comment.updateComment(commentRequestDto);
        Comment updatedComment = commentRepository.save(comment);

        return new CommentResponseDto(updatedComment);
    }

    public String deleteComment(Long id, CommentRequestDto commentRequestDto, String username) {
        User user = findUserByUsername(username);
        Comment comment = findByCommentId(id);
        if(comment.getUsername().equals(user.getUsername())) {
            commentRepository.deleteById(id);
            return "삭제 완료";
        } else {
            throw new IllegalArgumentException("Invalid commentDto Username");
        }
    }

    private Comment findByCommentId(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 댓글이 존재하지 않습니다."));
        return comment;
    }

    private ToDoList findToDoListById(Long id) {
        ToDoList toDoList = toDoListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ToDoList ID"));
        return toDoList;
    }

    private User findUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User ID"));
        return user;
    }
}
