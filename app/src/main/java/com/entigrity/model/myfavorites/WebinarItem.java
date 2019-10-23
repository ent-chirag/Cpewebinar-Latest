package com.entigrity.model.myfavorites;

import com.google.gson.annotations.SerializedName;


public class WebinarItem {
    public int getScheduleid() {
        return scheduleid;
    }

    public String getJoinurl() {
        return joinurl;
    }

    public void setJoinurl(String joinurl) {
        this.joinurl = joinurl;
    }

    public void setScheduleid(int scheduleid) {
        this.scheduleid = scheduleid;
    }

    public void setCertificatelink(String certificatelink) {
        this.certificatelink = certificatelink;
    }

    @SerializedName("fav_webinar_count")
    private int favWebinarCount;

    @SerializedName("join_url")
    private String joinurl;


    @SerializedName("speaker_name")
    private String speakerName;

    @SerializedName("schedule_id")
    private int scheduleid;

    @SerializedName("fee")
    private String fee;

    @SerializedName("webinar_image")
    private String webinarImage;

    @SerializedName("people_register_webinar")
    private int peopleRegisterWebinar;

    @SerializedName("time_zone")
    private String timeZone;

    @SerializedName("webinar_share_link")
    private String webinarShareLink;

    public String getPaymentlink() {
        return paymentlink;
    }

    public void setPaymentlink(String paymentlink) {
        this.paymentlink = paymentlink;
    }

    @SerializedName("duration")
    private int duration;

    @SerializedName("certificate_link")
    private String certificatelink;

    @SerializedName("payment_link")
    private String paymentlink;

    public String getCertificatelink() {
        return certificatelink;
    }

    @SerializedName("start_time")
    private String startTime;

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

    @SerializedName("webinar_type")
    private String webinarType;

    @SerializedName("webinar_like")
    private String webinarLike;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("status")
    private String status;

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

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
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

    public void setWebinarType(String webinarType) {
        this.webinarType = webinarType;
    }

    public String getWebinarType() {
        return webinarType;
    }

    public void setWebinarLike(String webinarLike) {
        this.webinarLike = webinarLike;
    }

    public String getWebinarLike() {
        return webinarLike;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "WebinarItem{" +
                        "fav_webinar_count = '" + favWebinarCount + '\'' +
                        "join_url = '" + joinurl + '\'' +
                        ",speaker_name = '" + speakerName + '\'' +
                        ",certificate_link = '" + certificatelink + '\'' +
                        ",payment_link = '" + paymentlink + '\'' +
                        ",fee = '" + fee + '\'' +
                        ",webinar_image = '" + webinarImage + '\'' +
                        ",people_register_webinar = '" + peopleRegisterWebinar + '\'' +
                        ",time_zone = '" + timeZone + '\'' +
                        ",webinar_share_link = '" + webinarShareLink + '\'' +
                        ",duration = '" + duration + '\'' +
                        ",start_time = '" + startTime + '\'' +
                        ",recorded_date = '" + recordedDate + '\'' +
                        ",company_name = '" + companyName + '\'' +
                        ",webinar_title = '" + webinarTitle + '\'' +
                        ",webinar_thumbnail_image = '" + webinarThumbnailImage + '\'' +
                        ",cpa_credit = '" + cpaCredit + '\'' +
                        ",id = '" + id + '\'' +
                        ",webinar_type = '" + webinarType + '\'' +
                        ",webinar_like = '" + webinarLike + '\'' +
                        ",start_date = '" + startDate + '\'' +
                        ",status = '" + status + '\'' +
                        ",schedule_id = '" + scheduleid + '\'' +
                        "}";
    }
}