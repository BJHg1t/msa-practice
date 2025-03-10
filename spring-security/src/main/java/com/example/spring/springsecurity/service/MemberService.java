package com.example.spring.springsecurity.service;

import com.example.spring.springsecurity.mapper.MemberMapper;
import com.example.spring.springsecurity.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public void signup(Member member) {
        memberMapper.saved(member);
    }
}
