package com.example.spring.springsecurity.controller;

import com.example.spring.google.model.Role;
import com.example.spring.springsecurity.model.User;
import com.example.spring.springsecurity.mapper.UserRepository;
import com.example.spring.springsecurity.config.jwt.TokenProvider;
import com.example.spring.springsecurity.dto.SignInResponse;
import com.example.spring.springsecurity.model.Member;
import com.example.spring.springsecurity.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @PostMapping("/google")
    public SignInResponse googleLogin(@AuthenticationPrincipal OAuth2User oAuth2User, HttpServletResponse response) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        User user = userRepository.findByEmail(email)
                .map(entity -> entity.update(name, picture))
                .orElse(User.builder()
                        .name(name)
                        .email(email)
                        .picture(picture)
                        .role(Role.USER)
                        .build());

        user = userRepository.save(user);

        Member member = Member.builder()
                .id(user.getId())
                .userId(user.getEmail())
                .userName(user.getName())
                .role(convertRole(user.getRole()))
                .build();

        String accessToken = tokenProvider.generateToken(member, Duration.ofHours(2));
        String refreshToken = tokenProvider.generateToken(member, Duration.ofDays(2));

        CookieUtil.addCookie(response, "refreshToken", refreshToken, 7*24*60*60);

        return SignInResponse.builder()
                .isLoggedIn(true)
                .userId(member.getUserId())
                .username(member.getUserId())
                .token(accessToken)
                .build();
    }

    private com.example.spring.springsecurity.type.Role convertRole(Role role) {
        switch (role) {
            case USER:
                return com.example.spring.springsecurity.type.Role.ROLE_USER;
            case ADMIN:
                return com.example.spring.springsecurity.type.Role.ROLE_ADMIN;
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}
