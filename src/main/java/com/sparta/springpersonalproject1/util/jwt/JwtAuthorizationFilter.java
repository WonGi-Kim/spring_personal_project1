package com.sparta.springpersonalproject1.util.jwt;

import com.sparta.springpersonalproject1.user.repository.UserRepository;
import com.sparta.springpersonalproject1.util.custom.Validator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(UserRepository userRepository, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = jwtUtil.getJwtFromHeader(req);
        if (tokenValue != null) {
            try {
                jwtUtil.validateToken(tokenValue);
                Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
                setAuthentication(info.getSubject());

                // 로그 추가: 토큰 정보 확인
                log.info("Decoded token: {}", tokenValue);
            } catch (JwtException e) {
                log.error(e.getMessage());
            }
        }
        filterChain.doFilter(req, res);
    }

    private void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        //UserDetails userDetails = (UserDetails) Validator.validateUser(userRepository.findByUsername(username));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}