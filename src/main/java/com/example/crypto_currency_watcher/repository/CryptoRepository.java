package com.example.crypto_currency_watcher.repository;

import com.example.crypto_currency_watcher.entity.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CryptoRepository extends JpaRepository<Crypto, Long> {
    @Query("select c from Crypto c where c.symbol = ?1")
    Crypto findBySymbol(String symbol);

    @Transactional
    @Modifying
    @Query("update Crypto set price_usd = ?1 where id = ?2")
    void updateCrypto(Double price, Long id);
}
