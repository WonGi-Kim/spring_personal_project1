package com.sparta.springpersonalproject1.controller;

import com.sparta.springpersonalproject1.dto.CustomResponse;
import com.sparta.springpersonalproject1.dto.userDto.UserRegisterReqeustDto;
import com.sparta.springpersonalproject1.dto.userDto.UserRegisterResponseDto;
import com.sparta.springpersonalproject1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.InputMismatchException;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    private ResponseEntity<CustomResponse<?>> signup(@RequestBody UserRegisterReqeustDto requestDto){
        try {
            UserRegisterResponseDto responseDto = userService.registerUser(requestDto);
            return ResponseEntity.ok().body(CustomResponse.makeResponse(responseDto, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(CustomResponse.makeResponse("This username already exists", HttpStatus.BAD_REQUEST));
        } catch (InputMismatchException e) {
            return ResponseEntity.badRequest().body(CustomResponse.makeResponse("Check your username or password. It can not be capitalized", HttpStatus.BAD_REQUEST));
        }
    }
}
