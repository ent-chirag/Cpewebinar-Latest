package com.myCPE.model.webinar_details;

import com.google.gson.annotations.SerializedName;

public class TestimonialsItem{

	@SerializedName("review")
	private String review;

	@SerializedName("rating")
	private int rating;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("id")
	private int id;

	@SerializedName("first_name")
	private String firstName;

	public void setReview(String review){
		this.review = review;
	}

	public String getReview(){
		return review;
	}

	public void setRating(int rating){
		this.rating = rating;
	}

	public int getRating(){
		return rating;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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
			"TestimonialsItem{" + 
			"review = '" + review + '\'' + 
			",rating = '" + rating + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",id = '" + id + '\'' + 
			",first_name = '" + firstName + '\'' + 
			"}";
		}
}