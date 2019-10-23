package com.entigrity.model.homewebinarlist;

import com.google.gson.annotations.SerializedName;


public class WebinarItem {

    @SerializedName("per_page")
    private int perPage;

    @SerializedName("fav_webinar_count")
    private int favWebinarCount;

    @SerializedName("speaker_name")
    private String speakerName;

    @SerializedName("fee")
    private String fee;

    @SerializedName("webinar_image")
    private String webinarImage;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("webina_type")
    private String webinaType;

    @SerializedName("people_register_webinar")
    private int peopleRegisterWebinar;

    @SerializedName("time_zone")
    private String timeZone;

    @SerializedName("webinar_share_link")
    private String webinarShareLink;

    @SerializedName("duration")
    private String duration;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("page_number")
    private String pageNumber;

    @SerializedName("recorded_date")
    private String recordedDate;

    @SerializedName("company_name")
    private String companyName;

    @SerializedName("webinar_title")
    private String webinarTitle;

    @SerializedName("webinar_thumbnail_image")
    private String webinarThumbnailImage;

    @SerializedName("cpa_credit")
    private String cpaCredit;

    @SerializedName("id")
    private int id;

    @SerializedName("webinar_like")
    private String webinarLike;

    @SerializedName("webinar_status")
    private String webinarStatus;

    @SerializedName("total_record")
    private int totalRecord;

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setFavWebinarCount(int favWebinarCount) {
        this.favWebinarCount = favWebinarCount;
    }

    public int getFavWebinarCount() {
        return favWebinarCount;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getFee() {
        return fee;
    }

    public void setWebinarImage(String webinarImage) {
        this.webinarImage = webinarImage;
    }

    public String getWebinarImage() {
        return webinarImage;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setWebinaType(String webinaType) {
        this.webinaType = webinaType;
    }

    public String getWebinaType() {
        return webinaType;
    }

    public void setPeopleRegisterWebinar(int peopleRegisterWebinar) {
        this.peopleRegisterWebinar = peopleRegisterWebinar;
    }

    public int getPeopleRegisterWebinar() {
        return peopleRegisterWebinar;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setWebinarShareLink(String webinarShareLink) {
        this.webinarShareLink = webinarShareLink;
    }

    public String getWebinarShareLink() {
        return webinarShareLink;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setRecordedDate(String recordedDate) {
        this.recordedDate = recordedDate;
    }

    public String getRecordedDate() {
        return recordedDate;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setWebinarTitle(String webinarTitle) {
        this.webinarTitle = webinarTitle;
    }

    public String getWebinarTitle() {
        return webinarTitle;
    }

    public void setWebinarThumbnailImage(String webinarThumbnailImage) {
        this.webinarThumbnailImage = webinarThumbnailImage;
    }

    public String getWebinarThumbnailImage() {
        return webinarThumbnailImage;
    }

    public void setCpaCredit(String cpaCredit) {
        this.cpaCredit = cpaCredit;
    }

    public String getCpaCredit() {
        return cpaCredit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setWebinarLike(String webinarLike) {
        this.webinarLike = webinarLike;
    }

    public String getWebinarLike() {
        return webinarLike;
    }

    public void setWebinarStatus(String webinarStatus) {
        this.webinarStatus = webinarStatus;
    }

    public String getWebinarStatus() {
        return webinarStatus;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    @Override
    public String toString() {
        return
                "WebinarItem{" +
                        "per_page = '" + perPage + '\'' +
                        ",fav_webinar_count = '" + favWebinarCount + '\'' +
                        ",speaker_name = '" + speakerName + '\'' +
                        ",fee = '" + fee + '\'' +
                        ",webinar_image = '" + webinarImage + '\'' +
                        ",end_time = '" + endTime + '\'' +
                        ",webina_type = '" + webinaType + '\'' +
                        ",people_register_webinar = '" + peopleRegisterWebinar + '\'' +
                        ",time_zone = '" + timeZone + '\'' +
                        ",webinar_share_link = '" + webinarShareLink + '\'' +
                        ",duration = '" + duration + '\'' +
                        ",start_time = '" + startTime + '\'' +
                        ",page_number = '" + pageNumber + '\'' +
                        ",recorded_date = '" + recordedDate + '\'' +
                        ",company_name = '" + companyName + '\'' +
                        ",webinar_title = '" + webinarTitle + '\'' +
                        ",webinar_thumbnail_image = '" + webinarThumbnailImage + '\'' +
                        ",cpa_credit = '" + cpaCredit + '\'' +
                        ",id = '" + id + '\'' +
                        ",webinar_like = '" + webinarLike + '\'' +
                        ",webinar_status = '" + webinarStatus + '\'' +
                        ",total_record = '" + totalRecord + '\'' +
                        "}";
    }
}


