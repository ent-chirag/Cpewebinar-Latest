package com.entigrity.model.webinar_details_new;

import com.google.gson.annotations.SerializedName;


public class WebinarTestimonialItem {

    @SerializedName("rate")
    private String rate;

    @SerializedName("review")
    private String review;

    @SerializedName("designation")
    private String designation;

    @SerializedName("date")
    private String date;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("first_name")
    private String firstName;

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReview() {
        return review;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return
                "WebinarTestimonialItem{" +
                        "rate = '" + rate + '\'' +
                        ",review = '" + review + '\'' +
                        ",last_name = '" + lastName + '\'' +
                        ",first_name = '" + firstName + '\'' +
                        ",designation = '" + designation + '\'' +
                        ",date = '" + date + '\'' +
                        "}";
    }
}