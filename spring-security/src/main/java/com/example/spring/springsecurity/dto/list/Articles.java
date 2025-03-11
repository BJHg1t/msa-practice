package com.example.spring.springsecurity.dto.list;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Articles {
    private Long id;
    private String title;
    private String userId;
    private LocalDateTime created;
    private LocalDateTime updated;
}
