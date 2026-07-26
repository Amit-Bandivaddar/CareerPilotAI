package com.amit.careerpilotai.service;

import com.amit.careerpilotai.dto.InterviewQuestionsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterviewQuestionService {

    @Autowired
    private GeminiService geminiService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public InterviewQuestionsResponse generateQuestions(String resumeText) {

        try {

            String response =
                    geminiService.generateInterviewQuestions(resumeText);

            return objectMapper.readValue(
                    response,
                    InterviewQuestionsResponse.class
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to parse interview questions.",
                    e
            );

        }

    }

}