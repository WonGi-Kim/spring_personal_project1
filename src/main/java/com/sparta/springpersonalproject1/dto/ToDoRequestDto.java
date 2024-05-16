package com.sparta.springpersonalproject1.dto;

import lombok.Getter;

@Getter
public class ToDoRequestDto {
    private String title;
    private String content;
    private String manager;
    private String password;
    private String date;
}
