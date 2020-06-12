package com.finalproj.final_springboot_proj.service;

import com.finalproj.final_springboot_proj.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    List<User> findAllUsers();
    void deleteUser(Long id);
    User findUserById(Long id);
    User findUserByEmail(String email);
}
