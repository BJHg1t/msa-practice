package com.example.spring.springsecurity.controller;

import com.example.spring.springsecurity.config.jwt.TokenProvider;
import com.example.spring.springsecurity.config.security.CustomUserDetails;
import com.example.spring.springsecurity.dto.*;
import com.example.spring.springsecurity.model.Member;
import com.example.spring.springsecurity.service.MemberService;
import com.example.spring.springsecurity.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @PostMapping("/join")
    public SignUpResponseDTO signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        memberService.signup(signUpRequestDTO.toMember(bCryptPasswordEncoder));
        return SignUpResponseDTO.builder()
                .successed(true)
                .build();
    }

    @PostMapping("/login")
    public SignInResponseDTO signIn(
            @RequestBody SignInRequestDTO signInRequestDTO,
            HttpServletResponse response
    ) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequestDTO.getUserId(),
                        signInRequestDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        Member member = ((CustomUserDetails) authenticate.getPrincipal()).getMember();

        String accessToken = tokenProvider.generateToken(member, Duration.ofHours(2));
        String refreshToken = tokenProvider.generateToken(member, Duration.ofDays(2));

        CookieUtil.addCookie(response, "refreshToken", refreshToken, 7*24*60*60);

        return SignInResponseDTO.builder()
                .isLoggedIn(true)
                .userId(member.getUserId())
                .username(member.getUserId())
                .token(accessToken)
                .build();
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, String name) {
        CookieUtil.deleteCookie(request, response, "refreshToken");
    }

    @GetMapping("/user/info")
    public UserInfoResponseDTO getUserInfo(HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        return UserInfoResponseDTO.builder()
                .id(member.getId())
                .userName(member.getUserName())
                .userId(member.getUserId())
                .role(member.getRole())
                .build();
    }
}
