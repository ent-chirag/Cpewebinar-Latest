package com.myCPE.model.review_question;

import com.google.gson.annotations.SerializedName;


public class A{

	@SerializedName("option_title")
	private String optionTitle;

	@SerializedName("is_answer")
	private String isAnswer;

	@SerializedName("description")
	private String Description;

	public void setOptionTitle(String optionTitle){
		this.optionTitle = optionTitle;
	}

	public String getOptionTitle(){
		return optionTitle;
	}

	public void setIsAnswer(String isAnswer){
		this.isAnswer = isAnswer;
	}

	public String getIsAnswer(){
		return isAnswer;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	@Override
 	public String toString(){
		return
			"A{" +
			"option_title = '" + optionTitle + '\'' +
			",is_answer = '" + isAnswer + '\'' +
                    ",description = '" + Description + '\'' +
			"}";
		}
}