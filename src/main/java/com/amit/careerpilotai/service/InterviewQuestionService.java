package com.amit.careerpilotai.service;

import com.amit.careerpilotai.dto.InterviewQuestionsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class InterviewQuestionService {

    @Autowired
    private GeminiService geminiService;

    public InterviewQuestionsResponse generateQuestions(String resumeText) {

        String prompt =
                """
                You are a technical interviewer.

                Read the following resume.

                Generate exactly 10 interview questions based only on the candidate's skills, projects and technologies.

                Return only the questions, one per line.

                Resume:
                """ + resumeText;

        String response = geminiService.askGemini(prompt);

        return new InterviewQuestionsResponse(
                Arrays.asList(response.split("\n"))
        );
    }
}