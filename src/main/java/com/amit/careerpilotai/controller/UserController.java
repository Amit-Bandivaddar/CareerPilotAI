package com.amit.careerpilotai.controller;

import com.amit.careerpilotai.dto.*;
import com.amit.careerpilotai.entity.User;
import com.amit.careerpilotai.service.FileStorageService;
import com.amit.careerpilotai.service.ResumeAnalysisService;
import com.amit.careerpilotai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

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

    @GetMapping("/skill-gap/{id}")
    public ResponseEntity<String> skillGap(
            @PathVariable Long id,
            @RequestParam String role) throws IOException {

        return ResponseEntity.ok(
                userService.analyzeSkillGap(id, role)
        );
    }

    @GetMapping("/career-roadmap/{id}")
    public ResponseEntity<String> careerRoadmap(
            @PathVariable Long id,
            @RequestParam String goal) throws IOException {

        return ResponseEntity.ok(
                userService.generateCareerRoadmap(id, goal)
        );
    }

    @GetMapping("/cover-letter/{id}")
    public ResponseEntity<String> generateCoverLetter(
            @PathVariable Long id,
            @RequestParam String company,
            @RequestParam String role) throws IOException {

        return ResponseEntity.ok(
                userService.generateCoverLetter(
                        id,
                        company,
                        role
                )
        );
    }
    @GetMapping("/resume-improvement/{id}")
    public ResponseEntity<String> resumeImprovement(
            @PathVariable Long id) throws IOException {

        return ResponseEntity.ok(
                userService.improveResume(id)
        );
    }
    @GetMapping("/ats-score/{id}")
    public ResponseEntity<String> atsScore(
            @PathVariable Long id) throws IOException {

        return ResponseEntity.ok(
                userService.generateATSScore(id)
        );
    }
    @PostMapping("/job-match/{id}")
    public ResponseEntity<String> jobMatch(
            @PathVariable Long id,
            @RequestBody JobMatchRequest request)
            throws IOException {

        return ResponseEntity.ok(
                userService.matchJobDescription(
                        id,
                        request.getJobDescription()
                )
        );
    }
    @GetMapping("/linkedin-summary/{id}")
    public ResponseEntity<String> linkedInSummary(
            @PathVariable Long id)
            throws IOException {

        return ResponseEntity.ok(
                userService.generateLinkedInSummary(id)
        );
    }
    @GetMapping("/hr-email/{id}")
    public ResponseEntity<String> hrEmail(
            @PathVariable Long id,
            @RequestParam String company,
            @RequestParam String role)
            throws IOException {

        return ResponseEntity.ok(
                userService.generateHREmail(
                        id,
                        company,
                        role
                )
        );
    }
}