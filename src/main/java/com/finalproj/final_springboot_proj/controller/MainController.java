package com.finalproj.final_springboot_proj.controller;

import com.finalproj.final_springboot_proj.model.Role;
import com.finalproj.final_springboot_proj.model.User;
import com.finalproj.final_springboot_proj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    private UserService service;

    private HttpSession session;

    private BCryptPasswordEncoder bCrypt;

    @Autowired
    public MainController(UserService service, HttpSession session, BCryptPasswordEncoder bCrypt) {
        this.service = service;
        this.session = session;
        this.bCrypt = bCrypt;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin() {
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String admin(ModelMap model) {
        List<User> users = service.findAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/admin/add")
    public String getAdd() {
        return "user-add";
    }

    @PostMapping("/admin/add")
    public String postAdd(@RequestParam(name = "user_role", required = false) Role userRole,
                          @RequestParam(name = "admin_role", required = false) Role adminRole,
                          User user) {

        HashSet<Role> roles = new HashSet<>();
        roles.add(userRole);
        roles.add(adminRole);
        user.setRoles(roles);

        String userBCryptPassword = bCrypt.encode(user.getPassword());

        user.setPassword(userBCryptPassword);

        service.saveUser(user);

        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        service.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/update/{id}")
    public String getUpdate(@PathVariable("id") Long id, ModelMap model) {
        User user = service.findUserById(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/admin/update")
    public String postUpdate(@RequestParam(name = "user_role", required = false) Role userRole,
                             @RequestParam(name = "admin_role", required = false) Role adminRole,
                             User user) {
        HashSet<Role> roles = new HashSet<>();
        roles.add(userRole);
        roles.add(adminRole);
        user.setRoles(roles);

        String userBCryptPassword = bCrypt.encode(user.getPassword());

        user.setPassword(userBCryptPassword);

        service.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String get(ModelMap model) {
        UserDetails userDetails = (UserDetails) session.getAttribute("username");
        User user = service.findUserByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        return "user-page";
    }
}
