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

                String result = response.getCandidates()
                        .get(0)
                        .getContent()
                        .getParts()
                        .get(0)
                        .getText();
                result = result
                        .replaceAll("(?m)^## ", "\n## ")
                        .replaceAll("(?m)^# ", "\n# ")
                        .replaceAll("(?m)^\\*\\*", "\n**")
                        .trim();

                return result;


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

Analyze the resume and return the response in VALID MARKDOWN.

Formatting Rules:

- Use '#' for headings.
- Use '##' for subheadings.
- Use '-' for bullet points.
- Leave one blank line after every heading.
- Leave one blank line between sections.
- Do NOT merge words together.
- Keep paragraphs short.
- Use bold text for important values.
- Make the response look professional.

Return EXACTLY in this structure:

# Resume Score

**Score:** XX/100

# Strengths

- Point 1
- Point 2
- Point 3

# Missing Skills

- Skill 1
- Skill 2
- Skill 3

# Suggestions

- Suggestion 1
- Suggestion 2
- Suggestion 3

# Best Suitable Roles

- Role 1
- Role 2
- Role 3

Resume:

"""
                + resumeText;

        return askGemini(prompt);
    }
}