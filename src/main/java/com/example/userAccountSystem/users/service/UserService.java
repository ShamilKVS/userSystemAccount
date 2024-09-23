package com.example.userAccountSystem.users.service;

import com.example.userAccountSystem.users.data.Purchase;
import com.example.userAccountSystem.users.data.User;
import com.example.userAccountSystem.users.data.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface UserService {

    @Transactional
    UserDto createUser(UserDto userDto);

    @Transactional
    void purchaseProduct(Long userId, Purchase purchase);

    @Transactional
    void sellProduct(Long userId, Purchase purchase);

    @Transactional
    void updateBalance(Long userId, BigDecimal amount);

    UserDto getUser(Long userId);

}
