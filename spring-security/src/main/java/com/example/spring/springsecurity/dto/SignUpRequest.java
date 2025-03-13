package com.example.spring.springsecurity.dto;

import com.example.spring.springsecurity.model.Member;
import com.example.spring.springsecurity.type.Role;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@ToString
public class SignUpRequest {
    private String userId;
    private String password;
    private String userName;
    private Role role;

    public Member toMember(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Member.builder()
                .userId(userId)
                .password(bCryptPasswordEncoder.encode(password))
                .userName(userName)
                .role(role)
                .build();
    }
}
