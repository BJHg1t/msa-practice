package com.example.spring.api.dto;

import lombok.Getter;

@Getter
public class SignInRequestDTO {
    private String userId;
    private String password;
}
