package com.example.spring.member.member.web;

import com.example.spring.member.member.domain.Member;
import com.example.spring.member.member.domain.MemberService;
import com.example.spring.member.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping()
    public Mono<Member> createUser(@RequestBody User user) {
        return memberService.createUser(user);
    }

    @GetMapping("/{isbn}")
    public Mono<Member> getUser(@PathVariable String isbn) {
        return memberService.findUserByIsbn(isbn);
    }
}
