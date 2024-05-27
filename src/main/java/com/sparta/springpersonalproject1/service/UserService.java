package com.sparta.springpersonalproject1.service;

import com.sparta.springpersonalproject1.dto.userDto.UserRegisterReqeustDto;
import com.sparta.springpersonalproject1.dto.userDto.UserRegisterResponseDto;
import com.sparta.springpersonalproject1.entity.User;
import com.sparta.springpersonalproject1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.InputMismatchException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRegisterResponseDto registerUser(UserRegisterReqeustDto requestDto) {
        // 중복된 username 확인
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }
        // parser
        if (requestDto.getUsername().matches(".*[A-Z].*") || requestDto.getPassword().matches(".*[A-Z].*")) {
            throw new InputMismatchException("대문자 포함됨");
        }

        User user = new User(requestDto);
        user.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
        return new UserRegisterResponseDto(user);
    }

}
