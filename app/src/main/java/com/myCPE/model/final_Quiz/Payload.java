package com.myCPE.model.final_Quiz;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Payload {

    @SerializedName("final_quiz_questions")
    private List<FinalQuizQuestionsItem> finalQuizQuestions;

    public void setFinalQuizQuestions(List<FinalQuizQuestionsItem> finalQuizQuestions) {
        this.finalQuizQuestions = finalQuizQuestions;
    }

    public List<FinalQuizQuestionsItem> getFinalQuizQuestions() {
        return finalQuizQuestions;
    }

    @Override
    public String toString() {
        return
                "Payload{" +
                        "final_quiz_questions = '" + finalQuizQuestions + '\'' +
                        "}";
    }
}