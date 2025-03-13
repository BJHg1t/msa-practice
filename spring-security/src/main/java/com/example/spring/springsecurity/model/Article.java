package com.example.spring.springsecurity.model;

import com.example.spring.springsecurity.dto.BoardDetailResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Article {
    private Long id;
    private String title;
    private String content;
    private String userId;
    private String filePath;
    private LocalDateTime created;
    private LocalDateTime updated;

    public BoardDetailResponse toBoardDetailResponseDTO() {
        return BoardDetailResponse.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .filePath(filePath)
                .created(created)
                .build();
    }
}
