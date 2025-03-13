package com.example.spring.springsecurity.controller;

import com.example.spring.springsecurity.dto.BoardDeleteRequest;
import com.example.spring.springsecurity.dto.BoardDetailResponse;
import com.example.spring.springsecurity.dto.list.Articles;
import com.example.spring.springsecurity.dto.list.BoardListResponse;
import com.example.spring.springsecurity.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping
    public void saveArticle(
            @RequestParam("hiddenUserId") String userId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("file") MultipartFile file
    ) {
        boardService.saveArticle(userId, title, content, file);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public BoardListResponse getBoards(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        List<Articles> articles = boardService.getBoardArticles(page, size);
        int totalArticleCnt = boardService.getTotalArticleCnt();
        boolean last = (page * size) >= totalArticleCnt;

        return BoardListResponse.builder()
                .articles(articles)
                .last(last)
                .build();
    }

    @GetMapping("/{id}")
    public BoardDetailResponse getBoardDetail(@PathVariable Long id) {
        return boardService.getBoardDetail(id)
                .toBoardDetailResponseDTO();
    }

    @GetMapping("/file/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = boardService.downloadFile(fileName);

        String encoded = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encoded)
                .body(resource);
    }

    @PutMapping
    public void updateArticle(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("hiddenUserId") String userId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("hiddenId") Long id,
            @RequestParam("hiddenFileFlag") boolean fileChanged,
            @RequestParam("hiddenFilePath") String filePath
    ) {
        boardService.updateArticle(id, title, content, file, fileChanged ,filePath);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id, @RequestBody BoardDeleteRequest requestDTO) {
        boardService.deleteBoardById(id, requestDTO);
    }
}
