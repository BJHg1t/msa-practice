package com.example.spring.api.mapper;

import com.example.spring.api.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void insertMember(Member member);
    Member selectMemberByUserId(String userId);
}
