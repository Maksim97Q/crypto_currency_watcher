package com.example.crypto_currency_watcher.service;

import com.example.crypto_currency_watcher.entity.Crypto;
import com.example.crypto_currency_watcher.entity.User;
import com.example.crypto_currency_watcher.repository.CryptoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class CryptoService {
    private CryptoRepository cryptoRepository;
    private RestTemplate restTemplate;

    @Autowired
    public void setCryptoRepository(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Crypto> findAllCrypto() {
        return cryptoRepository.findAll();
    }

    public Crypto findByCryptoSymbol(String symbol) {
        return cryptoRepository.findBySymbol(symbol);
    }

    @Transactional
    public void updateCoin() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String url = "https://api.coinlore.net/api/ticker/?id={id1},{id2},{id3}";
        ResponseEntity<List<Crypto>> cryptoList =
                restTemplate.exchange(url, HttpMethod.GET,
                        entity, new ParameterizedTypeReference<>() {
                        }, "80", "90", "48543");
        List<Crypto> cryptos = cryptoList.getBody();
        log.info(cryptos);

        for (Crypto id : Objects.requireNonNull(cryptos)) {
            if (findAllCrypto().isEmpty()) {
                cryptoRepository.saveAll(cryptos);
            } else {
                cryptoRepository.updateCrypto(id.getPrice_usd(), id.getId());
            }

            List<Crypto> cryptoByUsersNotNull = cryptoRepository.findCryptoByUsersNotNull();
            findNewPriceCryptoCompareOldPriceUser(cryptoByUsersNotNull);
        }
    }

    private void findNewPriceCryptoCompareOldPriceUser(List<Crypto> cryptoByUsersNotNull) {
        if (!cryptoByUsersNotNull.isEmpty()) {
            for (Crypto getIdCrypto : cryptoByUsersNotNull) {
                Double newPrice = getIdCrypto.getPrice_usd();
                Double oldPrice = getIdCrypto.getUsers().getPrice_usd();
                double pricePercent = calculatePriceChangePercent(newPrice, oldPrice);

                if (pricePercent > 1) {
                    Crypto crypto = cryptoRepository.findById(getIdCrypto.getId()).orElseThrow();
                    notifyUsers(crypto, getIdCrypto.getUsers(), pricePercent);
                }
            }
        }
    }

    private void notifyUsers(Crypto crypto, User user, Double prisePercent) {
        log.warn("symbol: " + crypto.getSymbol()
                + ", username: " + user.getUsername()
                + ", percent: " + prisePercent);
    }

    private double calculatePriceChangePercent(Double newPrice, Double oldPrice) {
        return ((newPrice - oldPrice) / oldPrice * 100);
    }
}
