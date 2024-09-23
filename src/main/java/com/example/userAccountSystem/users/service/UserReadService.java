package com.example.userAccountSystem.users.service;

import com.example.userAccountSystem.users.data.UserDto;

import java.util.List;

public interface UserReadService {
    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();
}
