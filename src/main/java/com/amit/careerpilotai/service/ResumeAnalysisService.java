package com.amit.careerpilotai.service;

import com.amit.careerpilotai.dto.ResumeFeedback;
import com.amit.careerpilotai.entity.User;
import com.amit.careerpilotai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeAnalysisService {

    @Autowired
    private PdfReaderService pdfReaderService;

    @Autowired
    private UserRepository userRepository;

    public ResumeFeedback analyzeResume(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getResumePath() == null) {
            throw new RuntimeException("Resume not uploaded.");
        }

        String resumeText = pdfReaderService.extractText(user.getResumePath());

        resumeText = resumeText.toLowerCase();

        List<String> strengths = new ArrayList<>();
        List<String> suggestions = new ArrayList<>();

        int score = 50;

        if (resumeText.contains("java")) {
            strengths.add("Java skill found");
            score += 10;
        } else {
            suggestions.add("Add Java skills");
        }

        if (resumeText.contains("spring")) {
            strengths.add("Spring Boot experience found");
            score += 10;
        } else {
            suggestions.add("Mention Spring Boot");
        }

        if (resumeText.contains("mysql")) {
            strengths.add("MySQL knowledge found");
            score += 10;
        } else {
            suggestions.add("Mention MySQL");
        }

        if (resumeText.contains("git")) {
            strengths.add("Git experience found");
            score += 10;
        } else {
            suggestions.add("Mention Git");
        }

        if (resumeText.contains("github")) {
            strengths.add("GitHub profile mentioned");
            score += 10;
        } else {
            suggestions.add("Add GitHub profile");
        }

        if (resumeText.contains("react")) {
            strengths.add("React skill found");
            score += 10;
        } else {
            suggestions.add("Mention React if you know it");
        }

        if (score > 100) {
            score = 100;
        }
        if (suggestions.isEmpty()) {
            suggestions.add("Excellent resume! Keep adding more real-world projects and certifications.");
        }

        return new ResumeFeedback(score, strengths, suggestions);
    }
}