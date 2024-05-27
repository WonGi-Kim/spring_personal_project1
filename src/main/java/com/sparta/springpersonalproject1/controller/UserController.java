package com.sparta.springpersonalproject1.controller;

import com.sparta.springpersonalproject1.Enum.UserRoleEnum;
import com.sparta.springpersonalproject1.dto.JwtRequestDto;
import com.sparta.springpersonalproject1.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

@RestController
@RequestMapping("/api/jwt")
public class UserController {
    private final JwtUtil jwtUtil;

    public UserController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/create-token")
    public String createJwt(HttpServletResponse res, @RequestBody JwtRequestDto jwtRequestDto) {
        String token = jwtUtil.generateToken(jwtRequestDto.getUsername(), UserRoleEnum.valueOf(jwtRequestDto.getRole()));
        jwtUtil.addJwtToCookie(token, res);
        return token;
    }

    @GetMapping("/get-token")
    public String getJwt(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        // JWT 토큰 substring
        String token = jwtUtil.substringToken(tokenValue);

        // 토큰 검증
        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("Token Error");
        }

        // 토큰에서 사용자 정보 가져오기
        Claims info = jwtUtil.getUserInfoFromToken(token);
        // 사용자 username
        String username = info.getSubject();
        System.out.println("username = " + username);
        // 사용자 권한
        String authority = (String) info.get(JwtUtil.AUTHORIZATION_KEY);
        System.out.println("authority = " + authority);

        return "getJwt : " + username + ", " + authority;
    }
}

