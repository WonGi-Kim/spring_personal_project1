package com.sparta.springpersonalproject1.entity;

import com.sparta.springpersonalproject1.dto.userDto.UserRegisterReqeustDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

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

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdOn;

    public User(UserRegisterReqeustDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.nickname = requestDto.getNickname();
        this.role = requestDto.getRole();
        this.createdOn = getCreatedOn();
    }
}
