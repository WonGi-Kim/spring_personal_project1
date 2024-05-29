package com.sparta.springpersonalproject1.user.service;

import com.sparta.springpersonalproject1.Enum.UserRoleEnum;
import com.sparta.springpersonalproject1.util.custom.CustomResponse;
import com.sparta.springpersonalproject1.user.dto.UserLoginRequestDto;
import com.sparta.springpersonalproject1.user.dto.UserLoginResponseDto;
import com.sparta.springpersonalproject1.user.dto.UserRegisterReqeustDto;
import com.sparta.springpersonalproject1.user.dto.UserRegisterResponseDto;
import com.sparta.springpersonalproject1.user.entity.User;
import com.sparta.springpersonalproject1.user.repository.UserRepository;
import com.sparta.springpersonalproject1.util.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Validated
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
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

    public ResponseEntity<CustomResponse<?>> loginUserAndCreateJwt(UserLoginRequestDto requestDto, HttpServletResponse res) {
        Optional<User> userOptional = userRepository.findByUsername(requestDto.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
                UserLoginResponseDto responseDto = new UserLoginResponseDto();
                responseDto.setUsername(user.getUsername());
                responseDto.setRole(user.getRole());

                String token = jwtUtil.generateToken(responseDto.getUsername(), UserRoleEnum.valueOf(responseDto.getRole()));
                jwtUtil.addJwtToCookie(token, res);

                return ResponseEntity.ok().body(CustomResponse.makeResponse(token, HttpStatus.OK));
            }
        }

        // 로그인 실패 시에는 CustomResponse를 다른 상태 코드와 함께 반환
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CustomResponse.makeResponse("Login failed", HttpStatus.UNAUTHORIZED));
    }

}
