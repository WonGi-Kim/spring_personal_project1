package com.sparta.springpersonalproject1.util.jwt;

import com.sparta.springpersonalproject1.Enum.UserRoleEnum;
import com.sparta.springpersonalproject1.util.custom.CustomResponse;
import com.sparta.springpersonalproject1.user.dto.UserLoginResponseDto;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {
    private final JwtUtil jwtUtil;

    public JwtController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/create-token")
    public ResponseEntity<CustomResponse<?>> createJwt(HttpServletResponse res, UserLoginResponseDto loginResponseDto) {
        String token = jwtUtil.generateToken(loginResponseDto.getUsername(), UserRoleEnum.valueOf(loginResponseDto.getRole()));
        jwtUtil.addJwtToCookie(token, res);
        return ResponseEntity.ok().body(CustomResponse.makeResponse(token, HttpStatus.OK));
    }

    @GetMapping("/get-token")
    public ResponseEntity<CustomResponse<?>>  getJwt(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
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

        String result = "getJwt : " + username + ", " + authority;
        return ResponseEntity.ok().body(CustomResponse.makeResponse(result, HttpStatus.OK));
    }
}

