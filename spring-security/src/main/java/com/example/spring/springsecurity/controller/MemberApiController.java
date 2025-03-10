package com.example.spring.springsecurity.controller;

import com.example.spring.springsecurity.dto.SignUpRequestDTO;
import com.example.spring.springsecurity.dto.SignUpResponseDTO;
import com.example.spring.springsecurity.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join")
    public SignUpResponseDTO signup(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        System.out.println(signUpRequestDTO);
        memberService.signup(signUpRequestDTO.toMember(bCryptPasswordEncoder));
        return SignUpResponseDTO.builder()
                .successed(true)
                .build();
    }
}
