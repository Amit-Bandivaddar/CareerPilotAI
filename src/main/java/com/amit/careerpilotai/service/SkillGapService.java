package com.amit.careerpilotai.service;

import org.springframework.stereotype.Service;

@Service
public class SkillGapService {

    private final GeminiService geminiService;

    public SkillGapService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String analyzeSkillGap(String resumeText, String targetRole) {

        String prompt = """
You are an experienced Technical Recruiter and ATS Resume Reviewer.

Compare the following resume with the target job role:

Target Role:
""" + targetRole + """

Provide the response in clean Markdown format.

Use the following structure exactly:

# Current Skills
- Bullet points

# Missing Skills
- Bullet points

# Learning Priority
1. Numbered list

# Suggested Projects
For each project include:
- Project Name
- Tech Stack
- Features

# Readiness Percentage
Give:
- Internship Readiness: XX%
- Full-time Readiness: XX%

# Final Advice
Write 5-8 bullet point suggestions.

Rules:
- Leave one blank line between every heading.
- Leave one blank line between every bullet list.
- Use proper spacing.
- Do NOT merge words together.
- Do NOT remove spaces between words.
- Make the output neat and readable.

Resume:

""" + resumeText;

        return geminiService.askGemini(prompt);
    }
}