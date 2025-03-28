package com.example.spring.info.domain;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByIsbn(String isbn);
    @Modifying
    @Transactional
    @Query("DELETE FROM user WHERE isbn = :isbn")
    void deleteByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
}