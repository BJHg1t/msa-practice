package com.example.spring.springsecurity.service;

import com.example.spring.springsecurity.config.jwt.TokenProvider;
import com.example.spring.springsecurity.dto.TokenResponse;
import com.example.spring.springsecurity.model.Member;
import com.example.spring.springsecurity.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private TokenProvider tokenProvider;

    public TokenResponse makeNewToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtil.getCookieValue(request, "refreshToken");

        if (refreshToken != null && tokenProvider.validToken(refreshToken) == 1) {
            Member member = tokenProvider.getTokenDetails(refreshToken);

            String newAccessToken = tokenProvider.generateToken(member, Duration.ofHours(2));
            String newRefreshToken = tokenProvider.generateToken(member, Duration.ofDays(2));

            CookieUtil.addCookie(response,"refreshToken", newRefreshToken, 7*24*60*60);

            response.setHeader(HttpHeaders.AUTHORIZATION, newAccessToken);

            return TokenResponse.builder()
                    .token(newAccessToken)
                    .build();
        } else {
            return null;
        }
    }
}
