package com.example.crypto_currency_watcher.config;

import com.example.crypto_currency_watcher.RunScheduler;
import com.example.crypto_currency_watcher.repository.CryptoRepository;
import com.example.crypto_currency_watcher.repository.UserRepository;
import com.example.crypto_currency_watcher.service.CryptoService;
import com.example.crypto_currency_watcher.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CryptoService cryptoService(CryptoRepository cryptoRepository) {
        return new CryptoService(cryptoRepository, restTemplate());
    }

    @Bean
    public UserService userService(UserRepository userRepository, CryptoRepository cryptoRepository) {
        return new UserService(userRepository, cryptoRepository);
    }

    @Bean
    public RunScheduler runScheduler(CryptoRepository cryptoRepository) {
        return new RunScheduler(cryptoService(cryptoRepository));
    }
}
