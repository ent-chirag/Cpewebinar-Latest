package com.myCPE.model.homewebinarnew;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RecentWebinarItem {

    @SerializedName("webinar_title")
    private String webinarTitle;

    @SerializedName("id")
    private int id;

    @SerializedName("webinar_type")
    private String webinarType;

    @SerializedName("webinar_image")
    private String webinarImage;

    public String getWebinarTitle() {
        return webinarTitle;
    }

    public void setWebinarTitle(String webinarTitle) {
        this.webinarTitle = webinarTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWebinarType() {
        return webinarType;
    }

    public void setWebinarType(String webinarType) {
        this.webinarType = webinarType;
    }

    public String getWebinarImage() {
        return webinarImage;
    }

    public void setWebinarImage(String webinarImage) {
        this.webinarImage = webinarImage;
    }

    @Override
    public String toString() {
        return
                "RecentWebinarItem{" +
                        "id = '" + id + '\'' +
                        "webinar_title = '" + webinarTitle + '\'' +
                        "webinar_type = '" + webinarType + '\'' +
                        "webinar_image = '" + webinarImage + '\'' +
                        "}";
    }
}