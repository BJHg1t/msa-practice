package com.example.spring.springsecurity.dto.list;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BoardListResponse {
    private List<Articles> articles;
    private boolean last;
}
