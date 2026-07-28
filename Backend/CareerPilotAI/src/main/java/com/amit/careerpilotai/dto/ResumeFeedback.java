package com.amit.careerpilotai.dto;

import java.util.List;

public class ResumeFeedback {

    private int score;
    private List<String> strengths;
    private List<String> suggestions;

    public ResumeFeedback() {
    }

    public ResumeFeedback(int score, List<String> strengths, List<String> suggestions) {
        this.score = score;
        this.strengths = strengths;
        this.suggestions = suggestions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getStrengths() {
        return strengths;
    }

    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}