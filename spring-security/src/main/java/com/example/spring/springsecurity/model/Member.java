package com.example.spring.springsecurity.model;

import com.example.spring.springsecurity.type.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {
    private long id;
    private String userId;
    private String password;
    private String userName;
    private Role role;
}
