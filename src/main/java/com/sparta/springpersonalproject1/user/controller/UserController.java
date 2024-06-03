package com.sparta.springpersonalproject1.user.controller;

import com.sparta.springpersonalproject1.util.jwt.JwtController;
import com.sparta.springpersonalproject1.util.custom.CustomResponse;
import com.sparta.springpersonalproject1.user.dto.UserLoginRequestDto;
import com.sparta.springpersonalproject1.user.dto.UserRegisterReqeustDto;
import com.sparta.springpersonalproject1.user.dto.UserRegisterResponseDto;
import com.sparta.springpersonalproject1.user.service.UserService;
import com.sparta.springpersonalproject1.util.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.InputMismatchException;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    private ResponseEntity<CustomResponse<?>> signup(@RequestBody @Valid UserRegisterReqeustDto requestDto){
        try {
            UserRegisterResponseDto responseDto = userService.registerUser(requestDto);
            return ResponseEntity.ok().body(CustomResponse.makeResponse(responseDto, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(CustomResponse.makeResponse("This username already exists", HttpStatus.BAD_REQUEST));
        } catch (InputMismatchException e) {
            return ResponseEntity.badRequest().body(CustomResponse.makeResponse("Check your username or password. It can not be capitalized", HttpStatus.BAD_REQUEST));
        }
    }

    @GetMapping("/login")
    private ResponseEntity<CustomResponse<?>> login(@RequestBody UserLoginRequestDto requestDto, HttpServletResponse httpServletResponse) {
        try {
            CustomResponse responseDto = userService.loginUserAndCreateJwt(requestDto, httpServletResponse);
            return ResponseEntity.ok().body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    CustomResponse.makeResponse("Username or password is incorrect.", HttpStatus.BAD_REQUEST));
        }
    }
}
