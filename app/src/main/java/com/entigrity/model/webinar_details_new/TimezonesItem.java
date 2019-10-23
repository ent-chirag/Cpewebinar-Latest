package com.entigrity.model.webinar_details_new;

import com.google.gson.annotations.SerializedName;


public class TimezonesItem {

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("timezone")
    private String timezone;

    public int getStartutctime() {
        return startutctime;
    }

    public void setStartutctime(int startutctime) {
        this.startutctime = startutctime;
    }

    @SerializedName("start_utc_time")
    private int startutctime;


    @SerializedName("timezone_short")
    private String timezoneShort;

    @SerializedName("schedule_id")
    private int scheduleId;

    @SerializedName("start_date")
    private String startDate;

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezoneShort(String timezoneShort) {
        this.timezoneShort = timezoneShort;
    }

    public String getTimezoneShort() {
        return timezoneShort;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return
                "TimezonesItem{" +
                        "start_time = '" + startTime + '\'' +
                        ",timezone = '" + timezone + '\'' +
                        ",timezone_short = '" + timezoneShort + '\'' +
                        ",schedule_id = '" + scheduleId + '\'' +
                        ",start_date = '" + startDate + '\'' +
                        ",start_utc_time = '" + startutctime + '\'' +
                        "}";
    }
}