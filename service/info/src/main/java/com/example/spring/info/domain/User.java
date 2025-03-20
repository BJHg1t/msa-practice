package com.example.spring.info.domain;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;

import java.time.Instant;

@Builder
public record User(
        @Id Long id,
        String isbn,
        String name,
        String num,
        int age,
        @Column("create_at")
        @CreatedDate
        Instant createAt,
        @Column("last_modified_at")
        @LastModifiedDate
        Instant lastModifiedAt,
        @Version
        int version
) {
}
