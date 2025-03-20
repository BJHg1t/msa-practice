package com.example.spring.info.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String isbn) {
        super("User not found: " + isbn);
    }
}
