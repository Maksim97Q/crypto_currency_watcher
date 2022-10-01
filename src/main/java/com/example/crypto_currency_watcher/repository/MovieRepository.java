package com.example.crypto_currency_watcher.repository;

import com.example.crypto_currency_watcher.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
