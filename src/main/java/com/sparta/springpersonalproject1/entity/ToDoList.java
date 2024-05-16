package com.sparta.springpersonalproject1.entity;

import com.sparta.springpersonalproject1.dto.ToDoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ToDoList {
    private Long id;
    private String title;
    private String content;
    private String manager;
    private String password;
    private String date;


    public ToDoList(ToDoRequestDto toDoRequestDto) {
        this.title = toDoRequestDto.getTitle();
        this.content = toDoRequestDto.getContent();
        this.manager = toDoRequestDto.getManager();
        this.password = toDoRequestDto.getPassword();
        this.date = toDoRequestDto.getDate();
    }
}
