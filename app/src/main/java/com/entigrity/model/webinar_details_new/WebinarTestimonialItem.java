package com.entigrity.model.webinar_details_new;

import com.google.gson.annotations.SerializedName;


public class WebinarTestimonialItem{

	@SerializedName("rate")
	private String rate;

	@SerializedName("review")
	private String review;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("first_name")
	private String firstName;

	public void setRate(String rate){
		this.rate = rate;
	}

	public String getRate(){
		return rate;
	}

	public void setReview(String review){
		this.review = review;
	}

	public String getReview(){
		return review;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	@Override
 	public String toString(){
		return 
			"WebinarTestimonialItem{" + 
			"rate = '" + rate + '\'' + 
			",review = '" + review + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",first_name = '" + firstName + '\'' + 
			"}";
		}
}