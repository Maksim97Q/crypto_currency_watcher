package com.example.crypto_currency_watcher.repository;

import com.example.crypto_currency_watcher.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.username = ?1")
    User findByUsername(String username);

    @Query("select u from User u where u.crypto is not null")
    List<User> findUserByCryptoNotNull();
}
