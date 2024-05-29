package com.sparta.springpersonalproject1.user.entity;

import com.sparta.springpersonalproject1.comment.entity.Comment;
import com.sparta.springpersonalproject1.todo.entity.ToDoList;
import com.sparta.springpersonalproject1.user.dto.UserRegisterReqeustDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor

public class User { // Entity에서 유효성 검사 제거
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Size(min = 4, max = 10)
    @Pattern(regexp = "[a-z0-9]+")
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 254) // 암호화 한 비밀번호의 길이
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdOn;

    @OneToMany(mappedBy = "user")
    private List<ToDoList> toDoLists = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    public User(UserRegisterReqeustDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.nickname = requestDto.getNickname();
        this.role = requestDto.getRole();
        this.createdOn = getCreatedOn();
    }
}


