package com.sparta.springpersonalproject1.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.springpersonalproject1.comment.dto.CommentRequestDto;
import com.sparta.springpersonalproject1.todo.entity.ToDoList;
import com.sparta.springpersonalproject1.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commentContent;
    private String username; // 나중에 회원가입 하면 변경 예정

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp commentDate;

    @ManyToOne
    @JoinColumn(name = "to_do_id_relate_comment")
    @JsonBackReference // 직렬화 순환 참조 방지
    private ToDoList toDoList;

    @ManyToOne
    @JoinColumn(name = "user_id_relate_comment")
    @JsonBackReference
    private User user;



    public Comment(CommentRequestDto commentRequestDto, ToDoList toDoList, User user) {
        this.commentContent = commentRequestDto.getCommentContent();
        this.username = commentRequestDto.getUsername();
        this.toDoList = toDoList;
        this.user = user;
        this.commentDate = getCommentDate(); // 현재 시간으로 설정
    }

    public void updateComment(CommentRequestDto commentRequestDto) {
        this.commentContent = commentRequestDto.getCommentContent();
    }
}

