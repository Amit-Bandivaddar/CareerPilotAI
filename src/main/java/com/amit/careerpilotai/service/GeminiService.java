package com.amit.careerpilotai.service;

import com.amit.careerpilotai.dto.GeminiRequest;
import com.amit.careerpilotai.dto.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder().build();

    // Generic method for sending any prompt to Gemini
    public String askGemini(String prompt) {

        try {

            // Print API Key (only first 10 characters)
            System.out.println("API Key Starts With: " + apiKey.substring(0, 10));

            GeminiRequest.Part part =
                    new GeminiRequest.Part(prompt);

            GeminiRequest.Content content =
                    new GeminiRequest.Content(List.of(part));

            GeminiRequest request =
                    new GeminiRequest(List.of(content));

            String url =
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent";
            GeminiResponse response = webClient.post()
                    .uri(url)
                    .header("X-goog-api-key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(GeminiResponse.class)
                    .block();

            if (response != null
                    && response.getCandidates() != null
                    && !response.getCandidates().isEmpty()) {

                return response.getCandidates()
                        .get(0)
                        .getContent()
                        .getParts()
                        .get(0)
                        .getText();
            }

            return "No response received from Gemini.";

        } catch (Exception e) {

            return """
                AI Service is currently unavailable.

                Possible reasons:
                - Daily Gemini API quota exceeded.
                - Invalid API Key.
                - Network Connection issue.

                Error:
                """ + e.getMessage();
        }
    }




    // Resume Analysis
    public String analyzeResume(String resumeText) {

        String prompt = """
                You are an expert ATS Resume Reviewer.

                Analyze the following resume and provide:

                1. Resume Score out of 100
                2. Strengths
                3. Missing Skills
                4. Suggestions for Improvement
                5. Best Suitable Job Role

                Resume:
                """
                + resumeText;

        return askGemini(prompt);
    }
}