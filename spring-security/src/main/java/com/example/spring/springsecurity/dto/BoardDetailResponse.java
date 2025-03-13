package com.example.spring.springsecurity.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardDetailResponse {
    private String title;
    private String content;
    private String userId;
    private String filePath;
    private LocalDateTime created;
}
