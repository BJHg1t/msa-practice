package com.example.spring.api.service;

import com.example.spring.api.mapper.MemberMapper;
import com.example.spring.api.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public void signUp(Member member) {
        memberMapper.insertMember(member);
    }
}
