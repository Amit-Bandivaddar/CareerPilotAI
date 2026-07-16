package com.amit.careerpilotai.service;

import com.amit.careerpilotai.dto.InterviewQuestionsResponse;
import com.amit.careerpilotai.dto.LoginResponse;
import com.amit.careerpilotai.dto.UpdateProfileRequest;
import com.amit.careerpilotai.entity.User;
import com.amit.careerpilotai.repository.UserRepository;
import com.amit.careerpilotai.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private InterviewQuestionService interviewQuestionService;

    @Autowired
    private SkillGapService skillGapService;

    @Autowired
    private CareerRoadmapService careerRoadmapService;

    @Autowired
    private CoverLetterService coverLetterService;

    @Autowired
    private ResumeImprovementService resumeImprovementService;

    @Autowired
    private ATSScoreService atsScoreService;

    @Autowired
    private JobMatchService jobMatchService;

    @Autowired
    private LinkedInSummaryService linkedInSummaryService;

    @Autowired
    private HREmailService hrEmailService;

    public User registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public LoginResponse loginUser(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found!");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid Password!");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(token);
    }

    public User updateProfile(Long id, UpdateProfileRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(request.getFullName());
        user.setCareerGoal(request.getCareerGoal());

        return userRepository.save(user);
    }

    public void saveResumePath(Long id, String resumePath) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setResumePath(resumePath);

        userRepository.save(user);
    }

    public String analyzeResume(Long id) throws IOException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resumeText =
                fileStorageService.readResume(user.getResumePath());

        return geminiService.analyzeResume(resumeText);
    }

    public InterviewQuestionsResponse generateInterviewQuestions(Long id) throws IOException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resumeText =
                fileStorageService.readResume(user.getResumePath());

        return interviewQuestionService.generateQuestions(resumeText);
    }

    public String analyzeSkillGap(Long id, String targetRole) throws IOException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resumeText =
                fileStorageService.readResume(user.getResumePath());

        return skillGapService.analyzeSkillGap(resumeText, targetRole);
    }
    public String generateCareerRoadmap(Long id, String careerGoal) throws IOException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resumeText =
                fileStorageService.readResume(user.getResumePath());

        return careerRoadmapService.generateRoadmap(resumeText, careerGoal);
    }
    public String generateCoverLetter(Long id,
                                      String company,
                                      String role) throws IOException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resumeText =
                fileStorageService.readResume(user.getResumePath());

        return coverLetterService.generateCoverLetter(
                resumeText,
                company,
                role
        );
    }

    public String improveResume(Long id) throws IOException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resumeText =
                fileStorageService.readResume(user.getResumePath());

        return resumeImprovementService.improveResume(resumeText);
    }
    public String generateATSScore(Long id) throws IOException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resumeText =
                fileStorageService.readResume(user.getResumePath());

        return atsScoreService.generateATSScore(resumeText);
    }
    public String matchJobDescription(Long id,
                                      String jobDescription) throws IOException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resumeText =
                fileStorageService.readResume(user.getResumePath());

        return jobMatchService.matchResume(
                resumeText,
                jobDescription
        );
    }
    public String generateLinkedInSummary(Long id) throws IOException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resumeText =
                fileStorageService.readResume(user.getResumePath());

        return linkedInSummaryService.generateSummary(resumeText);
    }
    public String generateHREmail(Long id,
                                  String company,
                                  String role) throws IOException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resumeText =
                fileStorageService.readResume(user.getResumePath());

        return hrEmailService.generateEmail(
                resumeText,
                company,
                role
        );
    }
}