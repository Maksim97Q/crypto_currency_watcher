package com.example.crypto_currency_watcher;

import com.example.crypto_currency_watcher.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class RunScheduler {
    private CryptoService cryptoService;

    @Autowired
    public void setCryptoService(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @Scheduled(fixedDelay = 8000)
    public void updateCrypto() {
        cryptoService.updateCoin();
    }

}
