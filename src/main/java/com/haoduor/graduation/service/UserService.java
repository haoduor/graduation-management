package com.haoduor.graduation.service;

import com.haoduor.graduation.model.User;

import java.util.List;

public interface UserService {
    public User getUserByName(String username);
    public List<User> getAllUsers();
}
