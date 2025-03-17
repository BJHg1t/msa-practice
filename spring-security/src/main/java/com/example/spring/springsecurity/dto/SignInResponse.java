package com.example.spring.springsecurity.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInResponse {
    private boolean isLoggedIn;
    private String userId;
    private String username;
    private String token;
}
