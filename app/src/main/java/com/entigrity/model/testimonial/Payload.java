package com.entigrity.model.testimonial;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Payload{

	@SerializedName("is_last")
	private boolean isLast;

	@SerializedName("webinar_testimonial")
	private List<WebinarTestimonialItem> webinarTestimonial;

	public void setIsLast(boolean isLast){
		this.isLast = isLast;
	}

	public boolean isIsLast(){
		return isLast;
	}

	public void setWebinarTestimonial(List<WebinarTestimonialItem> webinarTestimonial){
		this.webinarTestimonial = webinarTestimonial;
	}

	public List<WebinarTestimonialItem> getWebinarTestimonial(){
		return webinarTestimonial;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"is_last = '" + isLast + '\'' + 
			",webinar_testimonial = '" + webinarTestimonial + '\'' + 
			"}";
		}
}