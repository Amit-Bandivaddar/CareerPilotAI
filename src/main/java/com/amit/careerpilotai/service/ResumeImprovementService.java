package com.amit.careerpilotai.service;

import org.springframework.stereotype.Service;

@Service
public class ResumeImprovementService {

    private final GeminiService geminiService;

    public ResumeImprovementService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String improveResume(String resumeText) {

        String prompt = """
You are an expert ATS Resume Writer.

Analyze the resume and provide professional suggestions.

IMPORTANT FORMATTING RULES:

- Return the response in clean Markdown.
- Leave one blank line between every section.
- Use proper spacing between words.
- Use bullet points.

Return exactly in this format:

# Overall Evaluation

# Professional Summary Improvements

# Skills Improvements

# Project Improvements

# Experience Improvements

# ATS Keyword Suggestions

# Grammar & Formatting

# Final Improved Resume Tips

Resume:

""" + resumeText;

        return geminiService.askGemini(prompt);
    }
}