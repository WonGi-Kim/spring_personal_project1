package com.sparta.springpersonalproject1.entity;

import com.sparta.springpersonalproject1.dto.ToDoRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
