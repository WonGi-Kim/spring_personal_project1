package com.sparta.springpersonalproject1.util.jwt;

import com.sparta.springpersonalproject1.Enum.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    // Header KEY 값, Cookie의 name 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간 60분
    private final long TOKEN_TIME = 60 * 60 * 1000L;

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey, application.properties에 선언
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct // 최초 한 번만 받아오면 되는 값을 여러번 호출하는 실수를 방지하기 위한 어노테이션
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(String username, UserRoleEnum role) {
        Date date = new Date();
        return BEARER_PREFIX + Jwts.builder()
                .setSubject(username)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                .signWith(key, signatureAlgorithm)
                .claim(AUTHORIZATION_KEY, role)
                .compact();
    }

    // JWT Cookie 에 저장
    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
            cookie.setPath("/");

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
    }

    public String substringToken(String tokenValue) {
        // 토큰의 값 검사
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7); // BAERER_PREFIX 숫자의 길이가 7이므로 그 이후의 값을 짤라서 가져옴
        }
        System.out.println("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            System.out.println("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public Claims getUserInfoFromToken(String token) {
        // 담겨 있는 사용자의 정보를 사용 Key - value
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Claims getAllClaimsFromToken(String token) {
        Claims info = getUserInfoFromToken(token);
        return info;
    }

    public String getUsernameFromToken(String token) {
        token = substringToken(token);
        if (!validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }
        Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
    }
}