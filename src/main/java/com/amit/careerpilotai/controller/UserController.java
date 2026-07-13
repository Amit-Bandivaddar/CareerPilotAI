package com.amit.careerpilotai.controller;

import com.amit.careerpilotai.entity.User;
import com.amit.careerpilotai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }
    @PostMapping("/login")
    public User login(@RequestBody User user) {

        return userService.loginUser(user.getEmail(), user.getPassword());

    }
}