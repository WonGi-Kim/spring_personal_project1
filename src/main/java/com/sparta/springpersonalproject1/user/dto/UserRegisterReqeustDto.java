package com.sparta.springpersonalproject1.user.dto;

import jakarta.persistence.Basic;
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
    @Pattern(regexp = "[a-z0-9]+")
    private String username;

    @Size(min = 8, max = 16)
    @Pattern(regexp = "[a-z0-9]+")
    private String password;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Timestamp createdOn;

    private String role;
}
