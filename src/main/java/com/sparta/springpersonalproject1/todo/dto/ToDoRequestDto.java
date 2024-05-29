package com.sparta.springpersonalproject1.todo.dto;

import jakarta.persistence.Basic;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
public class ToDoRequestDto {
    private String title;
    private String content;
    //private String username;
    private String password;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Timestamp date;
    //private List<CommentResponseDto> commentResponse;
}
