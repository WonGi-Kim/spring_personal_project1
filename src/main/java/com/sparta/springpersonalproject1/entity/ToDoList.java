package com.sparta.springpersonalproject1.entity;

import com.sparta.springpersonalproject1.dto.ToDoRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ToDoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public void updateFromDto(ToDoRequestDto toDoRequestDto) {
        this.title = toDoRequestDto.getTitle();
        this.content = toDoRequestDto.getContent();
        this.manager = toDoRequestDto.getManager();
    }
}
