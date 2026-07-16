package com.amit.careerpilotai.service;

import org.springframework.stereotype.Service;

@Service
public class ATSScoreService {

    private final GeminiService geminiService;

    public ATSScoreService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String generateATSScore(String resumeText) {

        String prompt = """
You are an ATS Resume Expert.

Analyze this resume and return a professional ATS report.

IMPORTANT FORMATTING RULES:

- Return clean Markdown.
- Leave one blank line between sections.
- Use proper spacing.
- Use bullet points.

Return exactly in this format:

# ATS Score

**Overall Score:** XX/100

# Category Scores

- Formatting: XX/100
- Keywords: XX/100
- Skills: XX/100
- Projects: XX/100
- Education: XX/100

# Missing ATS Keywords

# Strengths

# Weaknesses

# ATS Improvement Suggestions

# Final Verdict

Resume:

""" + resumeText;

        return geminiService.askGemini(prompt);
    }
}