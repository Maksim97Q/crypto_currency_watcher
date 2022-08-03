package com.example.crypto_currency_watcher.repository;

import com.example.crypto_currency_watcher.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    @Transactional
    @Modifying
    @Query("update Currency set USD = ?1, EUR = ?2, RUB = ?3 where nameCompany = ?4")
    void updateCurrencyByNameCompany(Double USD, Double EUR, Double RUB, String nameCompany);
}
