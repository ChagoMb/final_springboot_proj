package com.finalproj.final_springboot_proj.controller;

import com.finalproj.final_springboot_proj.model.Role;
import com.finalproj.final_springboot_proj.model.User;
import com.finalproj.final_springboot_proj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private UserService userService;

    private BCryptPasswordEncoder bCrypt;

    @Autowired
    public RegistrationController(UserService userService, BCryptPasswordEncoder bCrypt) {
        this.userService = userService;
        this.bCrypt = bCrypt;
    }

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String postRegistration(@RequestParam(name = "user_role", required = false) Role userRole,
                                   @RequestParam(name = "admin_role", required = false) Role adminRole,
                                   User user) {

        HashSet<Role> roles = new HashSet<>();
        roles.add(userRole);
        roles.add(adminRole);
        user.setRoles(roles);

        String userBCryptPassword = bCrypt.encode(user.getPassword());

        user.setPassword(userBCryptPassword);

        userService.saveUser(user);

        return "login";
    }

}
