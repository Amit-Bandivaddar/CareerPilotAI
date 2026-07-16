package com.amit.careerpilotai.service;

import org.springframework.stereotype.Service;

@Service
public class LinkedInSummaryService {

    private final GeminiService geminiService;

    public LinkedInSummaryService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String generateSummary(String resumeText) {

        String prompt = """
You are a LinkedIn Profile Expert.

Generate a professional LinkedIn profile.

IMPORTANT FORMATTING RULES:

- Return clean Markdown.
- Leave one blank line between sections.
- Use proper spacing.

Return exactly in this format:

# Professional Headline

# About Section

# Top Skills

# Career Interests

# Suggested Hashtags

Resume:

""" + resumeText;

        return geminiService.askGemini(prompt);
    }
}