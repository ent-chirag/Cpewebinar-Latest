package com.myCPE.model.final_Quiz;

import com.google.gson.annotations.SerializedName;

public class FinalQuizQuestionsItem{

	@SerializedName("a")
	private A A;

	@SerializedName("b")
	private B B;

	@SerializedName("c")
	private C C;

	@SerializedName("d")
	private D D;

	@SerializedName("id")
	private int id;

	@SerializedName("question_title")
	private String questionTitle;

	@SerializedName("answer")
	private String answer;

	@SerializedName("is_correct")
	private boolean isCorrect;

	public void setA(A A){
		this.A = A;
	}

	public A getA(){
		return A;
	}

	public void setB(B B){
		this.B = B;
	}

	public B getB(){
		return B;
	}

	public void setC(C C){
		this.C = C;
	}

	public C getC(){
		return C;
	}

	public void setD(D D){
		this.D = D;
	}

	public D getD(){
		return D;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setQuestionTitle(String questionTitle){
		this.questionTitle = questionTitle;
	}

	public String getQuestionTitle(){
		return questionTitle;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean correct) {
		isCorrect = correct;
	}

	@Override
 	public String toString(){
		return 
			"FinalQuizQuestionsItem{" + 
			"a = '" + A + '\'' + 
			",b = '" + B + '\'' + 
			",c = '" + C + '\'' + 
			",d = '" + D + '\'' + 
			",id = '" + id + '\'' + 
			",question_title = '" + questionTitle + '\'' +
			",answer = '" + answer + '\'' +
			"}";
		}
}