package com.finalproj.final_springboot_proj.service;

import com.finalproj.final_springboot_proj.model.User;
import com.finalproj.final_springboot_proj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        repository.save(user);
    }

    @Override
    @Transactional
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public User findUserById(Long id) {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public User findUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }
}