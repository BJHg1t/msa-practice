package com.example.spring.springsecurity.service;

import com.example.spring.springsecurity.dto.BoardDeleteRequest;
import com.example.spring.springsecurity.dto.list.Articles;
import com.example.spring.springsecurity.mapper.BoardMapper;
import com.example.spring.springsecurity.model.Article;
import com.example.spring.springsecurity.model.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private final FileService fileService;

    @Transactional
    public void saveArticle(String userId, String title, String content, MultipartFile file) {
        String path = null;
        if (!file.isEmpty()) {
            path = fileService.fileUpload(file);
        }

        boardMapper.saveArticle(
                Article.builder()
                        .userId(userId)
                        .title(title)
                        .content(content)
                        .filePath(path)
                        .build()
        );
    }

    public List<Articles> getBoardArticles(int page, int size) {
        int offset = (page - 1) * size;
        return boardMapper.getArticles(
                Paging.builder()
                        .offset(offset)
                        .size(size)
                        .build()
        );
    }

    public int getTotalArticleCnt() {
        return boardMapper.getArticleCnt();
    }

    public Article getBoardDetail(Long id) {
        return boardMapper.getArticleById(id);
    }

    public Resource downloadFile(String fileName) {
        return fileService.downloadFile(fileName);
    }

    public void updateArticle(Long id, String title, String content, MultipartFile file, boolean fileChanged, String filePath) {
        String path = null;

        if (!file.isEmpty()) {
            path = fileService.fileUpload(file);
        }

        if (fileChanged) {
            fileService.deleteFile(filePath);
        } else {
            path = filePath;
        }

        boardMapper.updateArticle(
                Article.builder()
                        .id(id)
                        .title(title)
                        .content(content)
                        .filePath(path)
                        .build()
        );
    }

    public void deleteBoardById(Long id, BoardDeleteRequest requestDTO) {
        fileService.deleteFile(requestDTO.getFilePath());
        boardMapper.deleteBoardById(id);
    }
}
