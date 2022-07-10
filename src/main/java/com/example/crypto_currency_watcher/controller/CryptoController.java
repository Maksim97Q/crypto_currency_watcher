package com.example.crypto_currency_watcher.controller;

import com.example.crypto_currency_watcher.entity.Crypto;
import com.example.crypto_currency_watcher.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/crypto")
public class CryptoController {
    private CryptoService cryptoService;

    @Autowired
    public void setCryptoService(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Crypto>> getListCrypto() {
        return ResponseEntity.ok(cryptoService.findAllCrypto());
    }

    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<Crypto> getCryptoSymbol(@PathVariable String symbol) {
        return ResponseEntity.ok(cryptoService.findByCryptoSymbol(symbol));
    }
}
