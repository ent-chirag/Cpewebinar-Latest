package com.entigrity.model.Instructorlist_details;

import com.google.gson.annotations.SerializedName;


public class Speaker{

	@SerializedName("city")
	private String city;

	@SerializedName("expertise")
	private String expertise;

	@SerializedName("favourite")
	private int favourite;

	@SerializedName("follower_count")
	private int followerCount;

	@SerializedName("favorite_unfavorite_status")
	private String favoriteUnfavoriteStatus;

	@SerializedName("follow_unfollow_status")
	private String followUnfollowStatus;

	@SerializedName("contact_no")
	private String contactNo;

	@SerializedName("about_speaker")
	private String aboutSpeaker;

	@SerializedName("name")
	private String name;

	@SerializedName("logo")
	private String logo;

	@SerializedName("company")
	private String company;

	@SerializedName("id")
	private int id;

	@SerializedName("state")
	private String state;

	@SerializedName("email")
	private String email;

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setExpertise(String expertise){
		this.expertise = expertise;
	}

	public String getExpertise(){
		return expertise;
	}

	public void setFavourite(int favourite){
		this.favourite = favourite;
	}

	public int getFavourite(){
		return favourite;
	}

	public void setFollowerCount(int followerCount){
		this.followerCount = followerCount;
	}

	public int getFollowerCount(){
		return followerCount;
	}

	public void setFavoriteUnfavoriteStatus(String favoriteUnfavoriteStatus){
		this.favoriteUnfavoriteStatus = favoriteUnfavoriteStatus;
	}

	public String getFavoriteUnfavoriteStatus(){
		return favoriteUnfavoriteStatus;
	}

	public void setFollowUnfollowStatus(String followUnfollowStatus){
		this.followUnfollowStatus = followUnfollowStatus;
	}

	public String getFollowUnfollowStatus(){
		return followUnfollowStatus;
	}

	public void setContactNo(String contactNo){
		this.contactNo = contactNo;
	}

	public String getContactNo(){
		return contactNo;
	}

	public void setAboutSpeaker(String aboutSpeaker){
		this.aboutSpeaker = aboutSpeaker;
	}

	public String getAboutSpeaker(){
		return aboutSpeaker;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return logo;
	}

	public void setCompany(String company){
		this.company = company;
	}

	public String getCompany(){
		return company;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"Speaker{" + 
			"city = '" + city + '\'' + 
			",expertise = '" + expertise + '\'' + 
			",favourite = '" + favourite + '\'' + 
			",follower_count = '" + followerCount + '\'' + 
			",favorite_unfavorite_status = '" + favoriteUnfavoriteStatus + '\'' + 
			",follow_unfollow_status = '" + followUnfollowStatus + '\'' + 
			",contact_no = '" + contactNo + '\'' + 
			",about_speaker = '" + aboutSpeaker + '\'' + 
			",name = '" + name + '\'' + 
			",logo = '" + logo + '\'' + 
			",company = '" + company + '\'' + 
			",id = '" + id + '\'' + 
			",state = '" + state + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}