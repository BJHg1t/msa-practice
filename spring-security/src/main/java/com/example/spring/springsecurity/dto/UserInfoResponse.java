package com.example.spring.springsecurity.dto;

import com.example.spring.springsecurity.type.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {
    private Long id;
    private String userId;
    private String userName;
    private Role role;
}
