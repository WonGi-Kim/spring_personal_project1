package com.sparta.springpersonalproject1.comment.dto;

import com.sparta.springpersonalproject1.comment.entity.Comment;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class CommentResponseDto {
    private Long id;
    private String commentContent;
    private String username;
    private Long todoListId;
    private Long userId;
    private Timestamp commentDate;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.commentContent = comment.getCommentContent();
        this.username = comment.getUsername();
        this.todoListId = comment.getToDoList().getId();
        this.userId = comment.getUser().getId();
        this.commentDate = comment.getCommentDate();
    }
}
