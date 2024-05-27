package com.sparta.springpersonalproject1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.springpersonalproject1.dto.todoDto.ToDoRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    @Size(max = 200)
    private String content;

    @NotBlank(message = "필수")
    private String username;

    @NotBlank
    private String password;

    private String date;

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
        this.username = toDoRequestDto.getUsername();
        this.password = toDoRequestDto.getPassword();
        this.date = toDoRequestDto.getDate();
    }
    public void updateFromDto(ToDoRequestDto toDoRequestDto) {
        this.title = toDoRequestDto.getTitle();
        this.content = toDoRequestDto.getContent();
        this.username = toDoRequestDto.getUsername();
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
