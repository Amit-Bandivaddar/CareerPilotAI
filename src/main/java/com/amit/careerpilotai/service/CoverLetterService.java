package com.amit.careerpilotai.service;

import org.springframework.stereotype.Service;

@Service
public class CoverLetterService {

    private final GeminiService geminiService;

    public CoverLetterService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String generateCoverLetter(String resumeText,
                                      String company,
                                      String role) {

        String prompt = """
You are an experienced Technical Recruiter.

Generate a professional cover letter.

Return the response in Markdown.

Use this format:

# Cover Letter

Dear Hiring Manager,

...

Sincerely,

Candidate

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