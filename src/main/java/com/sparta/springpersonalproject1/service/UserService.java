package com.sparta.springpersonalproject1.service;

import com.sparta.springpersonalproject1.dto.userDto.UserRegisterReqeustDto;
import com.sparta.springpersonalproject1.dto.userDto.UserRegisterResponseDto;
import com.sparta.springpersonalproject1.entity.User;
import com.sparta.springpersonalproject1.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.sql.Timestamp;
import java.util.InputMismatchException;

@Service
@Validated
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRegisterResponseDto registerUser(UserRegisterReqeustDto requestDto) {
        // 중복된 username 확인
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (requestDto.getPassword().length() < 8 || requestDto.getPassword().length() > 16) {
            throw new IllegalArgumentException("Password length must be between 8 and 16 characters.");
        }


        User user = new User(requestDto);
        user.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(user);
        return new UserRegisterResponseDto(user);


    }

}
