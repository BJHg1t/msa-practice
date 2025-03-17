package com.example.spring.springsecurity.model;

import com.example.spring.springsecurity.type.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @NotNull
    private Role role;

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }
}
