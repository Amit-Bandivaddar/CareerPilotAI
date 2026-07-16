package com.amit.careerpilotai.service;

import org.springframework.stereotype.Service;

@Service
public class CareerRoadmapService {

    private final GeminiService geminiService;

    public CareerRoadmapService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String generateRoadmap(String resumeText, String careerGoal) {

        String prompt = """
You are an experienced Software Engineering Career Mentor.

Generate a personalized roadmap based on the resume.

Career Goal:
""" + careerGoal + """

Return the response in clean Markdown.

Use this structure:

# Current Level

# Month 1

# Month 2

# Month 3

# Month 4

# Recommended Projects

# Certifications

# Interview Preparation

# Learning Resources

# Final Advice

Resume:

""" + resumeText;

        return geminiService.askGemini(prompt);
    }
}