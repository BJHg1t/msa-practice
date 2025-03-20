package com.example.spring.info.service;

import com.example.spring.info.domain.User;
import com.example.spring.info.domain.UserRepository;
import com.example.spring.info.dto.UserRequest;
import com.example.spring.info.exception.UserAlreadyExistsException;
import com.example.spring.info.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Iterable<User> findUserList() {
        return userRepository.findAll();
    }

    public User findUser(String isbn) {
        return userRepository.findByIsbn(isbn)
                .orElseThrow(() -> new UserNotFoundException(isbn));
    }

    public User addUserToInfo(UserRequest userRequest) {
        if (userRepository.existsByIsbn(userRequest.getIsbn())) {
            throw new UserAlreadyExistsException(userRequest.getIsbn());
        }

        User user = User.builder()
                .isbn(userRequest.getIsbn())
                .name(userRequest.getName())
                .num(userRequest.getNum())
                .age(userRequest.getAge())
                .build();

        return userRepository.save(user);
    }

    public void deleteUserFromInfo(String isbn) {
        userRepository.deleteByIsbn(isbn);
    }

    public User updateUserInfo(String isbn, UserRequest userRequest) {
        return userRepository.findByIsbn(isbn)
                .map(
                        existingUser ->{
                            User update = User.builder()
                                    .id(existingUser.id())
                                    .isbn(isbn)
                                    .name(userRequest.getName())
                                    .num(userRequest.getNum())
                                    .age(userRequest.getAge())
                                    .createAt(existingUser.createAt())
                                    .lastModifiedAt(existingUser.lastModifiedAt())
                                    .version(existingUser.version())
                                    .build();
                            return userRepository.save(update);
                        }
                ).orElseGet(() -> userRepository.save(
                        User.builder()
                                .isbn(isbn)
                                .name(userRequest.getName())
                                .num(userRequest.getNum())
                                .age(userRequest.getAge())
                                .build()
                ));
    }
}
