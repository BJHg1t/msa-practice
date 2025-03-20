package com.example.spring.member.member.domain;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Table("history")
public record Member(
        @Id Long id,
        MemberStatus status,
        boolean success,
        @Version int version
) {
}
