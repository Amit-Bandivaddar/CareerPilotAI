package com.amit.careerpilotai.service;

import org.springframework.stereotype.Service;

@Service
public class JobMatchService {

    private final GeminiService geminiService;

    public JobMatchService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String matchResume(String resumeText, String jobDescription) {

        String prompt = """
You are an ATS Resume Expert.

Compare the resume with the given Job Description.

IMPORTANT FORMATTING RULES:

- Return clean Markdown.
- Leave one blank line between sections.
- Use bullet points.
- Use proper spacing.

Return exactly in this format:

# Match Score

**Overall Match:** XX%

# Matching Skills

# Missing Skills

# Missing Keywords

# Resume Strengths

# Resume Weaknesses

# Suggestions to Increase Match Score

# Interview Probability

Resume:

""" + resumeText +

                """
                
                Job Description:
                
                """ + jobDescription;

        return geminiService.askGemini(prompt);
    }
}