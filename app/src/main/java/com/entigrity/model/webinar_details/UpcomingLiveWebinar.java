package com.entigrity.model.webinar_details;

import com.google.gson.annotations.SerializedName;


public class UpcomingLiveWebinar {

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("timezone_format")
    private String timezoneFormat;

    @SerializedName("title")
    private String title;

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setTimezoneFormat(String timezoneFormat) {
        this.timezoneFormat = timezoneFormat;
    }

    public String getTimezoneFormat() {
        return timezoneFormat;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return
                "UpcomingLiveWebinar{" +
                        "start_time = '" + startTime + '\'' +
                        ",timezone_format = '" + timezoneFormat + '\'' +
                        ",title = '" + title + '\'' +
                        "}";
    }
}