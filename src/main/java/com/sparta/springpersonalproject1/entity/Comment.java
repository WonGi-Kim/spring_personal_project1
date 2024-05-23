package com.sparta.springpersonalproject1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String commentContent;

    @NotBlank
    @Size(max = 200)
    private String username; // 나중에 회원가입 하면 변경 예정

    @NotBlank
    private Long toDoId;

    private Timestamp commentDate;

    @ManyToOne
    @JoinColumn (name = "to_do_list_id")
    private ToDoList toDoList;


}
