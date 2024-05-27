package com.sparta.springpersonalproject1.dto.userDto;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
public class UserRegisterReqeustDto {
    private String nickname;

    @Size(min = 4, max = 10)
    @Pattern(regexp = "[a-z]+")
    @Pattern(regexp = "[0-9]+")
    @Column(nullable = false, unique = true)
    private String username;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "[a-z]+")
    @Pattern(regexp = "[0-9]+")
    @Column(nullable = false)
    private String password;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Timestamp createdOn;

    @Column(nullable = false)
    private String role;
}
