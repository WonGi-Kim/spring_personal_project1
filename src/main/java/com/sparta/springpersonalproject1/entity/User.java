package com.sparta.springpersonalproject1.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.springpersonalproject1.dto.userDto.UserRegisterReqeustDto;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Size(min = 4, max = 10)
    @Pattern(regexp = "[a-z0-9]+")
    @Column(nullable = false, unique = true)
    private String username;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "[a-z0-9]+")
    @Column(nullable = false)
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
