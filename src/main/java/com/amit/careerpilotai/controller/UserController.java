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
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@Tag(
        name = "User Management",
        description = "APIs for user registration, authentication, profile management, resume upload and AI-powered career services."
)
@RestController
@RequestMapping("/api/users")

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ResumeAnalysisService resumeAnalysisService;


    @Operation(
            summary = "Register a New User",
            description = "Creates a new user account in CareerPilotAI."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {

        User savedUser = userService.registerUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    @Operation(
            summary = "User Login",
            description = "Authenticates the user and returns a JWT token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody User user) {

        LoginResponse response =
                userService.loginUser(user.getEmail(), user.getPassword());

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get User Profile",
            description = "Returns the authenticated user's profile."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/profile")
    public String profile() {
        return "Welcome Amit! JWT Authentication Successful.";
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<User> updateProfile(
            @PathVariable Long id,
            @RequestBody UpdateProfileRequest request) {

        User updatedUser = userService.updateProfile(id, request);

        return ResponseEntity.ok(updatedUser);
    }
    @Operation(
            summary = "Upload Resume",
            description = "Uploads the user's resume in PDF format."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resume uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadResume(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file)
            throws IOException {

        String resumePath = fileStorageService.uploadResume(file);

        userService.saveResumePath(id, resumePath);

        return ResponseEntity.ok("Resume uploaded successfully.");
    }

    @Operation(
            summary = "Analyze Resume",
            description = "Uses Google Gemini AI to analyze the uploaded resume and generate ATS-style feedback."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resume analyzed successfully"),
            @ApiResponse(responseCode = "404", description = "Resume not found")
    })
    @GetMapping("/analyze-resume/{id}")
    public ResponseEntity<String> analyzeResume(@PathVariable Long id)
            throws IOException {

        return ResponseEntity.ok(
                userService.analyzeResume(id)
        );
    }

    @Operation(
            summary = "Generate Interview Questions",
            description = "Generates personalized interview questions based on the uploaded resume."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Interview questions generated successfully")
    })
    @GetMapping("/interview-questions/{id}")
    public ResponseEntity<InterviewQuestionsResponse> interviewQuestions(
            @PathVariable Long id)
            throws IOException {

        return ResponseEntity.ok(
                userService.generateInterviewQuestions(id)
        );
    }

    @Operation(
            summary = "Skill Gap Analysis",
            description = "Compares the user's resume against a target job role and identifies missing skills."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Skill gap analysis completed")
    })
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


    @Operation(
            summary = "Generate Cover Letter",
            description = "Generates a professional cover letter based on the uploaded resume and target job role using Google Gemini AI."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cover letter generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "User or resume not found"),
            @ApiResponse(responseCode = "500", description = "AI service unavailable")
    })
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

    @Operation(
            summary = "Resume Improvement Suggestions",
            description = "Provides AI-powered suggestions to improve the uploaded resume."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Suggestions generated successfully"),
            @ApiResponse(responseCode = "404", description = "Resume not found"),
            @ApiResponse(responseCode = "500", description = "AI service unavailable")
    })
    @GetMapping("/resume-improvement/{id}")
    public ResponseEntity<String> resumeImprovement(
            @PathVariable Long id) throws IOException {

        return ResponseEntity.ok(
                userService.improveResume(id)
        );
    }

    @Operation(
            summary = "ATS Score Dashboard",
            description = "Calculates the ATS compatibility score of the uploaded resume and provides detailed feedback."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ATS score generated successfully"),
            @ApiResponse(responseCode = "404", description = "Resume not found"),
            @ApiResponse(responseCode = "500", description = "AI service unavailable")
    })
    @GetMapping("/ats-score/{id}")
    public ResponseEntity<String> atsScore(
            @PathVariable Long id) throws IOException {

        return ResponseEntity.ok(
                userService.generateATSScore(id)
        );
    }

    @Operation(
            summary = "Job Description Match",
            description = "Compares the uploaded resume with a given job description and returns the matching percentage along with missing skills."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Job description match completed"),
            @ApiResponse(responseCode = "400", description = "Invalid job description"),
            @ApiResponse(responseCode = "404", description = "Resume not found"),
            @ApiResponse(responseCode = "500", description = "AI service unavailable")
    })
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

    @Operation(
            summary = "Generate LinkedIn Profile Summary",
            description = "Generates a professional LinkedIn 'About' section based on the uploaded resume."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LinkedIn summary generated successfully"),
            @ApiResponse(responseCode = "404", description = "Resume not found"),
            @ApiResponse(responseCode = "500", description = "AI service unavailable")
    })
    @GetMapping("/linkedin-summary/{id}")
    public ResponseEntity<String> linkedInSummary(
            @PathVariable Long id)
            throws IOException {

        return ResponseEntity.ok(
                userService.generateLinkedInSummary(id)
        );
    }

    @Operation(
            summary = "Generate HR Email",
            description = "Generates a professional email that can be sent to HR while applying for a job."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HR email generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Resume not found"),
            @ApiResponse(responseCode = "500", description = "AI service unavailable")
    })
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