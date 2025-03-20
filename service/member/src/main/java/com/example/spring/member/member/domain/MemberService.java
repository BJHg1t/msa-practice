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
                .map(createUser -> buildSuccessedMember(createUser))
                .defaultIfEmpty(buildfailedMember(user))
                .flatMap(memberRepository::save);
    }

    private static Member buildSuccessedMember(User user) {
        return Member.builder()
                .status(MemberStatus.CREATED)
                .success(true)
                .build();
    }

    // 유저 정보가 없으면 거부된 Member 객체 생성
    private static Member buildfailedMember(User user) {
        return Member.builder()
                .status(MemberStatus.CREATED)
                .success(false)
                .build();
    }
}
