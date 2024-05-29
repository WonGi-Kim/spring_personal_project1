package com.sparta.springpersonalproject1.comment.dto;

import jakarta.persistence.Basic;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
public class CommentRequestDto {
    private String commentContent;
    private String username;
    private Long toDoListId;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Timestamp commentDate;
}

