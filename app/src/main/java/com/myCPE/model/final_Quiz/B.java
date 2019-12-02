package com.myCPE.model.final_Quiz;

import com.google.gson.annotations.SerializedName;


public class B{

	@SerializedName("option_title")
	private String optionTitle;

	@SerializedName("is_answer")
	private String isAnswer;

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

	@Override
 	public String toString(){
		return 
			"B{" + 
			"option_title = '" + optionTitle + '\'' + 
			",is_answer = '" + isAnswer + '\'' + 
			"}";
		}
}