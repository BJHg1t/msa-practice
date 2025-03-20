package com.example.spring.info.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequest {
    @NotBlank(message = "The user ISBN must be defined.")
    private String isbn;
    @NotBlank(message = "The user name must be defined.")
    private String name;
    @NotBlank(message = "The user phone num must be defined.")
    private String num;
    @NotNull(message = "The user age must be defined.")
    @Positive(message = "The user age must be greater than zero")
    private int age;
}
