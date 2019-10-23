package com.entigrity.model.instructorfavorites;

import com.google.gson.annotations.SerializedName;

public class MyFavoriteSpeakerItem {

    @SerializedName("city")
    private String city;

    @SerializedName("speaker_name")
    private String speakerName;

    @SerializedName("speaker_id")
    private int speakerId;

    @SerializedName("rating")
    private String rating;

    @SerializedName("speaker_image")
    private String speakerImage;

    @SerializedName("favorite_unfavorite_status")
    private String favoriteUnfavoriteStatus;

    @SerializedName("follow_unfollow_status")
    private String followUnfollowStatus;

    @SerializedName("followers")
    private String followers;

    @SerializedName("area_of_expertise")
    private String areaOfExpertise;

    @SerializedName("about_speaker")
    private String aboutSpeaker;

    @SerializedName("company_name")
    private String companyName;

    @SerializedName("no_of_followers_count")
    private int noOfFollowersCount;

    @SerializedName("logo")
    private String logo;

    @SerializedName("state")
    private String state;

    @SerializedName("speaker_mobile_no")
    private String speakerMobileNo;

    @SerializedName("email")
    private String email;

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerId(int speakerId) {
        this.speakerId = speakerId;
    }

    public int getSpeakerId() {
        return speakerId;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setSpeakerImage(String speakerImage) {
        this.speakerImage = speakerImage;
    }

    public String getSpeakerImage() {
        return speakerImage;
    }

    public void setFavoriteUnfavoriteStatus(String favoriteUnfavoriteStatus) {
        this.favoriteUnfavoriteStatus = favoriteUnfavoriteStatus;
    }

    public String getFavoriteUnfavoriteStatus() {
        return favoriteUnfavoriteStatus;
    }

    public void setFollowUnfollowStatus(String followUnfollowStatus) {
        this.followUnfollowStatus = followUnfollowStatus;
    }

    public String getFollowUnfollowStatus() {
        return followUnfollowStatus;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowers() {
        return followers;
    }

    public void setAreaOfExpertise(String areaOfExpertise) {
        this.areaOfExpertise = areaOfExpertise;
    }

    public String getAreaOfExpertise() {
        return areaOfExpertise;
    }

    public void setAboutSpeaker(String aboutSpeaker) {
        this.aboutSpeaker = aboutSpeaker;
    }

    public String getAboutSpeaker() {
        return aboutSpeaker;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setNoOfFollowersCount(int noOfFollowersCount) {
        this.noOfFollowersCount = noOfFollowersCount;
    }

    public int getNoOfFollowersCount() {
        return noOfFollowersCount;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setSpeakerMobileNo(String speakerMobileNo) {
        this.speakerMobileNo = speakerMobileNo;
    }

    public String getSpeakerMobileNo() {
        return speakerMobileNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return
                "MyFavoriteSpeakerItem{" +
                        "city = '" + city + '\'' +
                        ",speaker_name = '" + speakerName + '\'' +
                        ",speaker_id = '" + speakerId + '\'' +
                        ",rating = '" + rating + '\'' +
                        ",speaker_image = '" + speakerImage + '\'' +
                        ",favorite_unfavorite_status = '" + favoriteUnfavoriteStatus + '\'' +
                        ",follow_unfollow_status = '" + followUnfollowStatus + '\'' +
                        ",followers = '" + followers + '\'' +
                        ",area_of_expertise = '" + areaOfExpertise + '\'' +
                        ",about_speaker = '" + aboutSpeaker + '\'' +
                        ",company_name = '" + companyName + '\'' +
                        ",no_of_followers_count = '" + noOfFollowersCount + '\'' +
                        ",logo = '" + logo + '\'' +
                        ",state = '" + state + '\'' +
                        ",speaker_mobile_no = '" + speakerMobileNo + '\'' +
                        ",email = '" + email + '\'' +
                        "}";
    }
}