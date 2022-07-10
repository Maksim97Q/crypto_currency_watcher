package com.example.crypto_currency_watcher.service;

import com.example.crypto_currency_watcher.entity.Crypto;
import com.example.crypto_currency_watcher.entity.User;
import com.example.crypto_currency_watcher.repository.CryptoRepository;
import com.example.crypto_currency_watcher.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private CryptoRepository cryptoRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setCryptoRepository(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    public void saveUser(User user) {
        Crypto bySymbol = cryptoRepository.findBySymbol(user.getSymbol());
        user.setPrice_usd(bySymbol.getPrice_usd());
        bySymbol.setUsers(user);
        userRepository.save(user);
    }
}
