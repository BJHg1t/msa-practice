package com.example.spring.info.controller;

import com.example.spring.info.domain.User;
import com.example.spring.info.dto.UserRequest;
import com.example.spring.info.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    private Iterable<User> getUsers() {
        return userService.findUserList();
    }

    @GetMapping("/{isbn}")
    public User getUser(@PathVariable String isbn) {
        return userService.findUser(isbn);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.addUserToInfo(userRequest);
    }

    @PutMapping("/{isbn}")
    public User updateUser(@PathVariable String isbn, @Valid @RequestBody UserRequest userRequest) {
        return userService.updateUserInfo(isbn, userRequest);
    }

    @DeleteMapping("/{isbn}")
    public void deleteUser(@PathVariable String isbn) {
        userService.deleteUserFromInfo(isbn);
    }
}