package com.sparta.springpersonalproject1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.springpersonalproject1.dto.commentDto.CommentRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(max = 200)
    private String commentContent;

    @NotBlank
    @Size(max = 200)
    private String username; // 나중에 회원가입 하면 변경 예정

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp commentDate;

    @ManyToOne
    @JoinColumn(name = "to_do_list_id")
    @JsonBackReference // 직렬화 순환 참조 방지
    private ToDoList toDoList;

    public Comment(CommentRequestDto commentRequestDto, ToDoList toDoList) {
        this.commentContent = commentRequestDto.getCommentContent();
        this.username = commentRequestDto.getUsername();
        this.toDoList = toDoList;
        this.commentDate = getCommentDate(); // 현재 시간으로 설정
    }

    public void updateComment(CommentRequestDto commentRequestDto) {
        this.commentContent = commentRequestDto.getCommentContent();
    }
}

