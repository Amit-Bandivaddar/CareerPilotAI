package com.amit.careerpilotai.controller;

import com.amit.careerpilotai.dto.LoginResponse;
import com.amit.careerpilotai.dto.ResumeFeedback;
import com.amit.careerpilotai.dto.UpdateProfileRequest;
import com.amit.careerpilotai.entity.User;
import com.amit.careerpilotai.service.FileStorageService;
import com.amit.careerpilotai.service.ResumeAnalysisService;
import com.amit.careerpilotai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.amit.careerpilotai.dto.InterviewQuestionsResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ResumeAnalysisService resumeAnalysisService;

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

    @PostMapping("/upload/{id}")
    public String uploadResume(@PathVariable Long id,
                               @RequestParam("file") MultipartFile file)
            throws IOException {

        String resumePath = fileStorageService.uploadResume(file);

        userService.saveResumePath(id, resumePath);

        return "Resume uploaded successfully.";
    }

    @GetMapping("/analyze-resume/{id}")
    public String analyzeResume(@PathVariable Long id) throws IOException {

        return userService.analyzeResume(id);
    }
    @GetMapping("/interview-questions/{id}")
    public InterviewQuestionsResponse interviewQuestions(@PathVariable Long id)
            throws IOException {

        return userService.generateInterviewQuestions(id);
    }
}