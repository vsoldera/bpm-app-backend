package com.akhianand.springrolejwt.service;

import com.akhianand.springrolejwt.model.UserDto;
import com.akhianand.springrolejwt.model.Users;

import java.util.List;

public interface UserService {
    Users save(UserDto user);
    List<Users> findAll();
    Users findOne(String username);
}
