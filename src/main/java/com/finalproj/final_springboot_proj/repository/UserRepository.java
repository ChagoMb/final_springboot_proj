package com.finalproj.final_springboot_proj.repository;

import com.finalproj.final_springboot_proj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
