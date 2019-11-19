package com.entigrity.model.homewebinarnew;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class WebinarItem {


    public String getRatingaverage() {
        return ratingaverage;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(int enrolled) {
        this.enrolled = enrolled;
    }

    public void setRatingaverage(String ratingaverage) {
        this.ratingaverage = ratingaverage;
    }

    @SerializedName("fav_webinar_count")
    private int favWebinarCount;

    @SerializedName("enrolled")
    private int enrolled;

    public int getRatingcount() {
        return ratingcount;
    }

    public void setRatingcount(int ratingcount) {
        this.ratingcount = ratingcount;
    }

    @SerializedName("rating_average")
    private String ratingaverage;

    @SerializedName("my_certificate_links")
    private List<MyCertificateLinksItem> myCertificateLinks;

    @SerializedName("rating_count")
    private int ratingcount;

    public String getWebinarlable() {
        return webinarlable;
    }

    public void setWebinarlable(String webinarlable) {
        this.webinarlable = webinarlable;
    }

    @SerializedName("webinar_lable")
    private String webinarlable;


    public String getPaymentlink() {
        return paymentlink;
    }

    public void setPaymentlink(String paymentlink) {
        this.paymentlink = paymentlink;
    }

    /*@SerializedName("certificate_link")
    private String certificatelink;*/

    @SerializedName("certificate_link")
    private List<String> certificateLink;


    @SerializedName("payment_link")
    private String paymentlink;


    @SerializedName("join_url")
    private String joinurl;

    public void setMyCertificateLinks(List<MyCertificateLinksItem> myCertificateLinks) {
        this.myCertificateLinks = myCertificateLinks;
    }

    public List<MyCertificateLinksItem> getMyCertificateLinks() {
        return myCertificateLinks;
    }

   /* public String getCertificatelink() {
        return certificatelink;
    }*//*

    public void setCertificaetlink(String certificatelink) {
        this.certificatelink = certificatelink;
    }*/

    public List<String> getCertificateLink() {
        return certificateLink;
    }

    public void setCertificateLink(List<String> certificateLink) {
        this.certificateLink = certificateLink;
    }

    public int getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(int scheduleid) {
        this.scheduleid = scheduleid;
    }

    @SerializedName("speaker_name")
    private String speakerName;

    @SerializedName("fee")
    private String fee;

    @SerializedName("schedule_id")
    private int scheduleid;

    @SerializedName("webinar_image")
    private String webinarImage;

    @SerializedName("people_register_webinar")
    private int peopleRegisterWebinar;

    @SerializedName("time_zone")
    private String timeZone;

    @SerializedName("webinar_share_link")
    private String webinarShareLink;

    @SerializedName("duration")
    private int duration;

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

    @SerializedName("is_card_save")
    private boolean isCardSave;

    @SerializedName("redirection_url")
    private String redirectionUrl;

    public String getJoinurl() {
        return joinurl;
    }

    public void setJoinurl(String joinurl) {
        this.joinurl = joinurl;
    }

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("status")
    private String status;

    public boolean isCardSave() {
        return isCardSave;
    }

    public void setCardSave(boolean cardSave) {
        isCardSave = cardSave;
    }

    public String getRedirectionUrl() {
        return redirectionUrl;
    }

    public void setRedirectionUrl(String redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
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
                        "enrolled = '" + enrolled + '\'' +
                        "webinar_lable = '" + webinarlable + '\'' +
                        "rating_average = '" + ratingaverage + '\'' +
                        "rating_count = '" + ratingcount + '\'' +
                        "join_url = '" + joinurl + '\'' +
                        ",speaker_name = '" + speakerName + '\'' +
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
                        ",my_certificate_links = '" + myCertificateLinks + '\'' +
                        "}";
    }
}