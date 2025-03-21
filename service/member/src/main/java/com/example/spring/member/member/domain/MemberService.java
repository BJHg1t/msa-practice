package com.example.spring.member.member.domain;

import com.example.spring.member.user.User;
import com.example.spring.member.user.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserClient userClient;

    public Mono<Member> createUser(User user) {
        return userClient.createUser(user)
                .map(createUser -> buildSuccessCreate(createUser))
                .defaultIfEmpty(buildfailCreate(user))
                .flatMap(memberRepository::save);
    }

    private static Member buildSuccessCreate(User user) {
        return Member.builder()
                .status(MemberStatus.CREATED)
                .success(true)
                .build();
    }

    // 유저 정보가 없으면 거부된 Member 객체 생성
    private static Member buildfailCreate(User user) {
        return Member.builder()
                .status(MemberStatus.CREATED)
                .success(false)
                .build();
    }

    public Mono<Member> findUserByIsbn(String isbn) {
        return userClient.getUserByIsbn(isbn)
                .map(user -> buildSuccessRead(user))
                .defaultIfEmpty(buildfailRead(isbn))
                .flatMap(memberRepository::save);
    }

    private static Member buildSuccessRead(User user) {
        return Member.builder()
                .status(MemberStatus.READ)
                .success(true)
                .build();
    }

    private static Member buildfailRead(String isbn) {
        return Member.builder()
                .status(MemberStatus.READ)
                .success(false)
                .build();
    }
}
