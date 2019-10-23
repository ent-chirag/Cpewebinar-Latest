package com.entigrity.model.review_question;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Payload{

	@SerializedName("review_questions")
	private List<ReviewQuestionsItem> reviewQuestions;

	public void setReviewQuestions(List<ReviewQuestionsItem> reviewQuestions){
		this.reviewQuestions = reviewQuestions;
	}

	public List<ReviewQuestionsItem> getReviewQuestions(){
		return reviewQuestions;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"review_questions = '" + reviewQuestions + '\'' + 
			"}";
		}
}