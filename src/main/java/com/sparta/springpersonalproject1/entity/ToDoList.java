package com.sparta.springpersonalproject1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.springpersonalproject1.dto.todoDto.ToDoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    private String username;
    private String password;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp date;

    @OneToMany(mappedBy = "toDoList", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public ToDoList(ToDoRequestDto toDoRequestDto) {
        this.title = toDoRequestDto.getTitle();
        this.content = toDoRequestDto.getContent();
        this.username = getUsername();
        this.password = toDoRequestDto.getPassword();
        this.date = getDate();
    }

    public ToDoList(ToDoRequestDto toDoRequestDto, User user) {
        this.title = toDoRequestDto.getTitle();
        this.content = toDoRequestDto.getContent();
        this.username = getUsername();
        this.password = toDoRequestDto.getPassword();
        this.user = user;
        this.date = getDate();
    }

    public void updateFromDto(ToDoRequestDto toDoRequestDto) {
        this.title = toDoRequestDto.getTitle();
        this.content = toDoRequestDto.getContent();
        this.username = getUsername();
    }

    public void validatePassword(String inputPassword) {
        if (!inputPassword.equals(this.getPassword())) {
            throw new IllegalArgumentException("입력한 비밀번호가 일치하지 않습니다.");
        }
    }

    public void validatePassword(ToDoRequestDto toDoRequestDto) {
        if (!toDoRequestDto.getPassword().equals(this.getPassword())) {
            throw new IllegalArgumentException("입력한 비밀번호가 일치하지 않습니다.");
        }
    }
}