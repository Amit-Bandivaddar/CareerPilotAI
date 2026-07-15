package com.amit.careerpilotai.controller;

import com.amit.careerpilotai.entity.User;
import com.amit.careerpilotai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.amit.careerpilotai.dto.LoginResponse;
import com.amit.careerpilotai.dto.UpdateProfileRequest;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.amit.careerpilotai.service.FileStorageService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody User user) {

        return userService.loginUser(user.getEmail(), user.getPassword());

    }
    @GetMapping("/profile")
    public String profile() {
        return "Welcome Amit! JWT Authentication Successful.";
    }
    @PutMapping("/profile/{id}")
    public User updateProfile(@PathVariable Long id,
                              @RequestBody UpdateProfileRequest request) {

        return userService.updateProfile(id, request);
    }
    @PostMapping("/upload")
    public String uploadResume(@RequestParam("file") MultipartFile file) throws IOException {

        String fileName = fileStorageService.uploadResume(file);

        return "Resume uploaded successfully: " + fileName;
    }
}