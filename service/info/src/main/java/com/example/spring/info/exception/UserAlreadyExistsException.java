package com.example.spring.info.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String isbn) {
        super("User already exists: " + isbn);
    }
}
