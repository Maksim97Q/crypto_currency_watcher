package com.example.crypto_currency_watcher.repository;

import com.example.crypto_currency_watcher.entity.Crypto;
import com.example.crypto_currency_watcher.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CryptoRepository extends JpaRepository<Crypto, Long> {
    @Query("select id from Crypto")
    List<Long> findAllId();

    @Query("select c from Crypto c where c.symbol = ?1")
    Crypto findBySymbol(String symbol);

    @Transactional
    @Modifying
    @Query("update Crypto set price_usd = ?1 where id = ?2")
    void updateCrypto(Double price, Long id);

    @Query("select c from Crypto c where c.users is not null")
    List<Crypto> findCryptoByUsersNotNull();
}
