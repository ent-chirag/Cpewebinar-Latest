package com.myCPE.model.company_details_new;

import com.google.gson.annotations.SerializedName;

public class Company{

	@SerializedName("company_profile_pic")
	private String companyProfilePic;

	@SerializedName("company_id")
	private int companyId;

	@SerializedName("city")
	private String city;

	@SerializedName("company_website")
	private String companyWebsite;

	@SerializedName("rating")
	private String rating;

	@SerializedName("review_count")
	private int reviewCount;

	@SerializedName("upcoming_webinar_count")
	private int upcomingWebinarCount;

	@SerializedName("company_description")
	private String companyDescription;

	@SerializedName("no_of_webinar")
	private int noOfWebinar;

	@SerializedName("past_webinar_count")
	private int pastWebinarCount;

	@SerializedName("selfstudy_webinar_count")
	private int selfstudyWebinarCount;

	@SerializedName("no_of_followers")
	private int noOfFollowers;

	@SerializedName("company_name")
	private String companyName;

	@SerializedName("state")
	private String state;

	@SerializedName("no_of_professionals_trained")
	private int noOfProfessionalsTrained;

	@SerializedName("is_following")
	private int isFollowing;

	public void setCompanyProfilePic(String companyProfilePic){
		this.companyProfilePic = companyProfilePic;
	}

	public String getCompanyProfilePic(){
		return companyProfilePic;
	}

	public void setCompanyId(int companyId){
		this.companyId = companyId;
	}

	public int getCompanyId(){
		return companyId;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setCompanyWebsite(String companyWebsite){
		this.companyWebsite = companyWebsite;
	}

	public String getCompanyWebsite(){
		return companyWebsite;
	}

	public void setRating(String rating){
		this.rating = rating;
	}

	public String getRating(){
		return rating;
	}

	public void setReviewCount(int reviewCount){
		this.reviewCount = reviewCount;
	}

	public int getReviewCount(){
		return reviewCount;
	}

	public void setUpcomingWebinarCount(int upcomingWebinarCount){
		this.upcomingWebinarCount = upcomingWebinarCount;
	}

	public int getUpcomingWebinarCount(){
		return upcomingWebinarCount;
	}

	public void setCompanyDescription(String companyDescription){
		this.companyDescription = companyDescription;
	}

	public String getCompanyDescription(){
		return companyDescription;
	}

	public void setNoOfWebinar(int noOfWebinar){
		this.noOfWebinar = noOfWebinar;
	}

	public int getNoOfWebinar(){
		return noOfWebinar;
	}

	public void setPastWebinarCount(int pastWebinarCount){
		this.pastWebinarCount = pastWebinarCount;
	}

	public int getPastWebinarCount(){
		return pastWebinarCount;
	}

	public void setNoOfFollowers(int noOfFollowers){
		this.noOfFollowers = noOfFollowers;
	}

	public int getNoOfFollowers(){
		return noOfFollowers;
	}

	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}

	public String getCompanyName(){
		return companyName;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setNoOfProfessionalsTrained(int noOfProfessionalsTrained){
		this.noOfProfessionalsTrained = noOfProfessionalsTrained;
	}

	public int getNoOfProfessionalsTrained(){
		return noOfProfessionalsTrained;
	}

	public void setIsFollowing(int isFollowing){
		this.isFollowing = isFollowing;
	}

	public int getIsFollowing(){
		return isFollowing;
	}

	public int getSelfstudyWebinarCount() {
		return selfstudyWebinarCount;
	}

	public void setSelfstudyWebinarCount(int selfstudyWebinarCount) {
		this.selfstudyWebinarCount = selfstudyWebinarCount;
	}

	@Override
 	public String toString(){
		return 
			"Company{" + 
			"company_profile_pic = '" + companyProfilePic + '\'' + 
			",company_id = '" + companyId + '\'' + 
			",city = '" + city + '\'' + 
			",company_website = '" + companyWebsite + '\'' + 
			",rating = '" + rating + '\'' + 
			",review_count = '" + reviewCount + '\'' + 
			",upcoming_webinar_count = '" + upcomingWebinarCount + '\'' + 
			",selfstudy_webinar_count = '" + selfstudyWebinarCount + '\'' +
			",company_description = '" + companyDescription + '\'' +
			",no_of_webinar = '" + noOfWebinar + '\'' + 
			",past_webinar_count = '" + pastWebinarCount + '\'' + 
			",no_of_followers = '" + noOfFollowers + '\'' + 
			",company_name = '" + companyName + '\'' + 
			",state = '" + state + '\'' + 
			",no_of_professionals_trained = '" + noOfProfessionalsTrained + '\'' + 
			",is_following = '" + isFollowing + '\'' + 
			"}";
		}
}