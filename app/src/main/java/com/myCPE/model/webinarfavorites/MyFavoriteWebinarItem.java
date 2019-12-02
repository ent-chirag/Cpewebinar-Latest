package com.myCPE.model.webinarfavorites;

import com.google.gson.annotations.SerializedName;


public class MyFavoriteWebinarItem {

    @SerializedName("date")
    private String date;

    @SerializedName("webinat_title")
    private String webinatTitle;

    @SerializedName("credit_no")
    private String creditNo;

    @SerializedName("thumb_image")
    private String thumbImage;

    @SerializedName("speaker_name")
    private String speakerName;

    @SerializedName("company_name")
    private String companyName;

    @SerializedName("fee")
    private String fee;

    public int getFavoritescount() {
        return favoritescount;
    }

    public void setFavoritescount(int favoritescount) {
        this.favoritescount = favoritescount;
    }

    @SerializedName("favorites_count")
    private int favoritescount;


    @SerializedName("webinat_id")
    private int webinatId;

    @SerializedName("time")
    private String time;

    @SerializedName("view_number")
    private String viewNumber;

    @SerializedName("webinar_duration_min")
    private String webinarDurationMin;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setWebinatTitle(String webinatTitle) {
        this.webinatTitle = webinatTitle;
    }

    public String getWebinatTitle() {
        return webinatTitle;
    }

    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo;
    }

    public String getCreditNo() {
        return creditNo;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getFee() {
        return fee;
    }

    public void setWebinatId(int webinatId) {
        this.webinatId = webinatId;
    }

    public int getWebinatId() {
        return webinatId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setViewNumber(String viewNumber) {
        this.viewNumber = viewNumber;
    }

    public String getViewNumber() {
        return viewNumber;
    }

    public void setWebinarDurationMin(String webinarDurationMin) {
        this.webinarDurationMin = webinarDurationMin;
    }

    public String getWebinarDurationMin() {
        return webinarDurationMin;
    }

    @Override
    public String toString() {
        return
                "MyFavoriteWebinarItem{" +
                        "date = '" + date + '\'' +
                        ",webinat_title = '" + webinatTitle + '\'' +
                        ",credit_no = '" + creditNo + '\'' +
                        ",thumb_image = '" + thumbImage + '\'' +
                        ",speaker_name = '" + speakerName + '\'' +
                        ",company_name = '" + companyName + '\'' +
                        ",fee = '" + fee + '\'' +
                        ",favorites_count = '" + favoritescount + '\'' +
                        ",webinat_id = '" + webinatId + '\'' +
                        ",time = '" + time + '\'' +
                        ",view_number = '" + viewNumber + '\'' +
                        ",webinar_duration_min = '" + webinarDurationMin + '\'' +
                        "}";
    }
}