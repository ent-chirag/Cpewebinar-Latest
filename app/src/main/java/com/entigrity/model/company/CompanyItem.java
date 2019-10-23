package com.entigrity.model.company;

import com.google.gson.annotations.SerializedName;


public class CompanyItem {

    @SerializedName("number_of_webinar")
    private int numberOfWebinar;

    public int getFavouriteunfavoritestatus() {
        return favouriteunfavoritestatus;
    }

    public void setFavouriteunfavoritestatus(int favouriteunfavoritestatus) {
        this.favouriteunfavoritestatus = favouriteunfavoritestatus;
    }

    @SerializedName("favourite_unfavorite_status")
    private int favouriteunfavoritestatus;


    @SerializedName("website")
    private String website;

    @SerializedName("number_of_speaker")
    private int numberOfSpeaker;

    @SerializedName("name")
    private String name;

    @SerializedName("logo")
    private String logo;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private int id;

    @SerializedName("contact_number")
    private String contactNumber;

    public void setNumberOfWebinar(int numberOfWebinar) {
        this.numberOfWebinar = numberOfWebinar;
    }

    public int getNumberOfWebinar() {
        return numberOfWebinar;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setNumberOfSpeaker(int numberOfSpeaker) {
        this.numberOfSpeaker = numberOfSpeaker;
    }

    public int getNumberOfSpeaker() {
        return numberOfSpeaker;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    @Override
    public String toString() {
        return
                "CompanyItem{" +
                        "number_of_webinar = '" + numberOfWebinar + '\'' +
                        "favourite_unfavorite_status = '" + favouriteunfavoritestatus + '\'' +
                        ",website = '" + website + '\'' +
                        ",number_of_speaker = '" + numberOfSpeaker + '\'' +
                        ",name = '" + name + '\'' +
                        ",logo = '" + logo + '\'' +
                        ",description = '" + description + '\'' +
                        ",id = '" + id + '\'' +
                        ",contact_number = '" + contactNumber + '\'' +
                        "}";
    }
}