package com.sparta.springpersonalproject1.dto.todoDto;

import com.sparta.springpersonalproject1.dto.commentDto.CommentResponseDto;
import com.sparta.springpersonalproject1.entity.ToDoList;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public class ToDoResponseDto {
    private Long id;
    private String title;
    private String todo;
    private String username;
    private String password;
    private Timestamp date;
    private List<CommentResponseDto> comments;
    private Long userId;

    public ToDoResponseDto(ToDoList toDoList) {
        this.id = toDoList.getId();
        this.title = toDoList.getTitle();
        this.todo = toDoList.getContent();
        this.userId = toDoList.getUser().getId();
        this.username = toDoList.getUsername();
        this.date = toDoList.getDate();
        this.comments = toDoList.getComments().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public ToDoResponseDto(ToDoList toDoList, boolean isPassword) {
        this.id = toDoList.getId();
        this.title = toDoList.getTitle();
        this.todo = toDoList.getContent();
        this.userId = toDoList.getUser().getId();
        this.username = toDoList.getUsername();
        this.date = toDoList.getDate();
        if (isPassword) {
            this.password = toDoList.getPassword();
        }

    }

}
