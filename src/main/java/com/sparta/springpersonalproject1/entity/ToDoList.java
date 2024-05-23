package com.sparta.springpersonalproject1.entity;

import com.sparta.springpersonalproject1.dto.ToDoRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String manager;

    @NotBlank
    private String password;

    private String date;

    @OneToMany(mappedBy = "toDoList", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

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
