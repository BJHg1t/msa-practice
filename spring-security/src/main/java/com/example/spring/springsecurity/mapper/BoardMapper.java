package com.example.spring.springsecurity.mapper;

import com.example.spring.springsecurity.dto.list.Articles;
import com.example.spring.springsecurity.model.Article;
import com.example.spring.springsecurity.model.Paging;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void saveArticle(Article article);
    List<Articles> getArticles(Paging page);
    int getArticleCnt();
    Article getArticleById(Long id);
    void updateArticle(Article article);
    void deleteBoardById(Long id);
}
