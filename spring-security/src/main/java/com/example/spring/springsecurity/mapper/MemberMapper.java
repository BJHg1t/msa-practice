package com.example.spring.springsecurity.mapper;

import com.example.spring.springsecurity.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void saved(Member member);
    Member findByUserId(String userId);
}
