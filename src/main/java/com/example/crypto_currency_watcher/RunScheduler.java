package com.example.crypto_currency_watcher;

import com.example.crypto_currency_watcher.service.CryptoService;
import org.springframework.scheduling.annotation.Scheduled;

public class RunScheduler {
    private final CryptoService cryptoService;

    public RunScheduler(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @Scheduled(fixedDelay = 5000)
    public void updateCrypto() {
        cryptoService.updateCoin();
    }
}
