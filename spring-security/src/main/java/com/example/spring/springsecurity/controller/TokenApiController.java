package com.example.spring.springsecurity.controller;

import com.example.spring.springsecurity.dto.TokenResponse;
import com.example.spring.springsecurity.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        TokenResponse newToken = tokenService.makeNewToken(request, response);

        return ResponseEntity.ok(newToken);
    }
}
