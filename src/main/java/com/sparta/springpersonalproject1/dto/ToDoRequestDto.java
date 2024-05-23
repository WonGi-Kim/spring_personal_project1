package com.sparta.springpersonalproject1.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ToDoRequestDto {
    private String title;
    private String content;
    private String manager;
    private String password;
    private String date;
    //private List<CommentResponseDto> commentResponse;
}
