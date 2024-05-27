package com.sparta.springpersonalproject1.dto.userDto;

import com.sparta.springpersonalproject1.entity.User;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class UserRegisterResponseDto {
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String role;
    private Timestamp createdOn;

    public UserRegisterResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.createdOn = user.getCreatedOn();
        this.nickname = user.getNickname();
    }
}
