package com.sparta.springpersonalproject1.dto.commentDto;

import com.sparta.springpersonalproject1.entity.Comment;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class CommentResponseDto {
    private Long id;
    private String commentContent;
    private String username;
    private Long todoListId;
    private Timestamp commentDate;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.commentContent = comment.getCommentContent();
        this.username = comment.getUsername();
        this.todoListId = comment.getToDoList().getId();
        this.commentDate = comment.getCommentDate();
    }
}
