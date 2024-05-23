package com.sparta.springpersonalproject1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String commentContent;
    private String username;
    private Long toDoListId;
    private String commentDate;
}

