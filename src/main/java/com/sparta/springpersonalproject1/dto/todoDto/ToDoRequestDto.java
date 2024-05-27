package com.sparta.springpersonalproject1.dto.todoDto;

import lombok.Getter;

@Getter
public class ToDoRequestDto {
    private String title;
    private String content;
    private String username;
    private String password;
    private String date;
    //private List<CommentResponseDto> commentResponse;
}
