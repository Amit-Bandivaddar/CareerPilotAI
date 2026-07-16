package com.amit.careerpilotai.dto;

import java.util.List;

public class InterviewQuestionsResponse {

    private List<String> questions;

    public InterviewQuestionsResponse() {
    }

    public InterviewQuestionsResponse(List<String> questions) {
        this.questions = questions;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }
}