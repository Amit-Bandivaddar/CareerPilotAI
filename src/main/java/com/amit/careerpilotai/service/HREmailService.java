package com.amit.careerpilotai.service;

import org.springframework.stereotype.Service;

@Service
public class HREmailService {

    private final GeminiService geminiService;

    public HREmailService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String generateEmail(String resumeText,
                                String company,
                                String role) {

        String prompt = """
You are an HR Recruiter.

Generate a professional internship/job application email.

Return clean Markdown.

Use this structure:

# Subject

# Email

# Closing

Company:
""" + company +

                """
                
                Role:
                """ + role +

                """
                
                Resume:
                
                """ + resumeText;

        return geminiService.askGemini(prompt);
    }
}