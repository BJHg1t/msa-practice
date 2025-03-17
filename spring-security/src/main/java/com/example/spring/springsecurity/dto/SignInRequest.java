package com.example.spring.springsecurity.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignInRequest {
    private String userId;
    private String password;
}
