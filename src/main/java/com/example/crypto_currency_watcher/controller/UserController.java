package com.example.crypto_currency_watcher.controller;

import com.example.crypto_currency_watcher.entity.User;
import com.example.crypto_currency_watcher.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody User user) {
        boolean saveUser = userService.saveUser(user);
        return saveUser
                ? new ResponseEntity<>(user, (HttpStatus.CREATED))
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
