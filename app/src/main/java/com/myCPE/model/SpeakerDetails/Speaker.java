package com.myCPE.model.SpeakerDetails;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Speaker{

	@SerializedName("city")
	private String city;

	@SerializedName("speaker_id")
	private int speakerId;

	@SerializedName("speaker_name")
	private String speakerName;

	@SerializedName("rating")
	private String rating;

	@SerializedName("review_count")
	private int reviewCount;

	@SerializedName("speaker_profile_pic")
	private String speakerProfilePic;

	@SerializedName("speaker_description")
	private String speakerDescription;

	@SerializedName("upcoming_webinar_count")
	private int upcomingWebinarCount;

	@SerializedName("selfstudy_webinar_count")
	private int selfstudyWebinarCount;

	@SerializedName("speaker_linked_in_profile")
	private String speakerLinkedInProfile;

	@SerializedName("no_of_webinar")
	private int noOfWebinar;

	@SerializedName("past_webinar_count")
	private int pastWebinarCount;

	@SerializedName("no_of_followers")
	private int noOfFollowers;

	@SerializedName("area_of_expertise")
	private List<String> areaOfExpertise;

	@SerializedName("speaker_website")
	private String speakerWebsite;

	@SerializedName("speaker_designation")
	private String speakerDesignation;

	@SerializedName("state")
	private String state;

	@SerializedName("is_following")
	private int isFollowing;

	@SerializedName("no_of_professionals_trained")
	private int noOfProfessionalsTrained;

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setSpeakerId(int speakerId){
		this.speakerId = speakerId;
	}

	public int getSpeakerId(){
		return speakerId;
	}

	public void setSpeakerName(String speakerName){
		this.speakerName = speakerName;
	}

	public String getSpeakerName(){
		return speakerName;
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

	public void setSpeakerProfilePic(String speakerProfilePic){
		this.speakerProfilePic = speakerProfilePic;
	}

	public String getSpeakerProfilePic(){
		return speakerProfilePic;
	}

	public void setSpeakerDescription(String speakerDescription){
		this.speakerDescription = speakerDescription;
	}

	public String getSpeakerDescription(){
		return speakerDescription;
	}

	public void setUpcomingWebinarCount(int upcomingWebinarCount){
		this.upcomingWebinarCount = upcomingWebinarCount;
	}

	public int getUpcomingWebinarCount(){
		return upcomingWebinarCount;
	}

	public void setSpeakerLinkedInProfile(String speakerLinkedInProfile){
		this.speakerLinkedInProfile = speakerLinkedInProfile;
	}

	public String getSpeakerLinkedInProfile(){
		return speakerLinkedInProfile;
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

	public void setAreaOfExpertise(List<String> areaOfExpertise){
		this.areaOfExpertise = areaOfExpertise;
	}

	public List<String> getAreaOfExpertise(){
		return areaOfExpertise;
	}

	public void setSpeakerWebsite(String speakerWebsite){
		this.speakerWebsite = speakerWebsite;
	}

	public String getSpeakerWebsite(){
		return speakerWebsite;
	}

	public void setSpeakerDesignation(String speakerDesignation){
		this.speakerDesignation = speakerDesignation;
	}

	public String getSpeakerDesignation(){
		return speakerDesignation;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setIsFollowing(int isFollowing){
		this.isFollowing = isFollowing;
	}

	public int getIsFollowing(){
		return isFollowing;
	}

	public void setNoOfProfessionalsTrained(int noOfProfessionalsTrained){
		this.noOfProfessionalsTrained = noOfProfessionalsTrained;
	}

	public int getNoOfProfessionalsTrained(){
		return noOfProfessionalsTrained;
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
			"Speaker{" + 
			"city = '" + city + '\'' + 
			",speaker_id = '" + speakerId + '\'' + 
			",speaker_name = '" + speakerName + '\'' + 
			",rating = '" + rating + '\'' + 
			",review_count = '" + reviewCount + '\'' + 
			",speaker_profile_pic = '" + speakerProfilePic + '\'' + 
			",speaker_description = '" + speakerDescription + '\'' + 
			",upcoming_webinar_count = '" + upcomingWebinarCount + '\'' + 
			",selfstudy_webinar_count = '" + selfstudyWebinarCount + '\'' +
			",speaker_linked_in_profile = '" + speakerLinkedInProfile + '\'' +
			",no_of_webinar = '" + noOfWebinar + '\'' + 
			",past_webinar_count = '" + pastWebinarCount + '\'' + 
			",no_of_followers = '" + noOfFollowers + '\'' + 
			",area_of_expertise = '" + areaOfExpertise + '\'' + 
			",speaker_website = '" + speakerWebsite + '\'' + 
			",speaker_designation = '" + speakerDesignation + '\'' + 
			",state = '" + state + '\'' + 
			",is_following = '" + isFollowing + '\'' + 
			",no_of_professionals_trained = '" + noOfProfessionalsTrained + '\'' + 
			"}";
		}
}