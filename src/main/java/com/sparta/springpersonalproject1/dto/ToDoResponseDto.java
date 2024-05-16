package com.sparta.springpersonalproject1.dto;

import com.sparta.springpersonalproject1.entity.ToDoList;
import lombok.Getter;

@Getter
public class ToDoResponseDto {
    private Long id;
    private String title;
    private String todo;
    private String manager;
    private String password;
    private String date;

    public ToDoResponseDto(ToDoList toDoList) {
        this.id = toDoList.getId();
        this.title = toDoList.getTitle();
        this.todo = toDoList.getContent();
        this.manager = toDoList.getManager();
        this.password = toDoList.getPassword();
        this.date = toDoList.getDate();
    }
}