package com.entigrity.model.My_Credit;

import com.google.gson.annotations.SerializedName;


public class MyCreditsItem {

    @SerializedName("host_date")
    private String hostDate;

    @SerializedName("webinar_id")
    private String webinarId;

    public String getJoinUrl() {
        return joinUrl;
    }

    public void setJoinUrl(String joinUrl) {
        this.joinUrl = joinUrl;
    }

    @SerializedName("subject")
    private String subject;


    @SerializedName("joinUrl")
    private String joinUrl;

    @SerializedName("speaker_name")
    private String speakerName;

    @SerializedName("certificate_link")
    private String certificateLink;

    @SerializedName("webinar_title")
    private String webinarTitle;

    @SerializedName("webinar_type")
    private String webinarType;

    @SerializedName("credit")
    private String credit;


    public String getWebinarCreditType() {
        return WebinarCreditType;
    }

    public void setWebinarCreditType(String webinarCreditType) {
        WebinarCreditType = webinarCreditType;
    }

    public String getCeCredit() {
        return ceCredit;
    }

    public void setCeCredit(String ceCredit) {
        this.ceCredit = ceCredit;
    }

    @SerializedName("webinar_credit_type")
    private String WebinarCreditType;

    @SerializedName("ce_credit")
    private String ceCredit;


    @SerializedName("webinar_status")
    private String webinarStatus;

    public void setHostDate(String hostDate) {
        this.hostDate = hostDate;
    }

    public String getHostDate() {
        return hostDate;
    }

    public void setWebinarId(String webinarId) {
        this.webinarId = webinarId;
    }

    public String getWebinarId() {
        return webinarId;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setCertificateLink(String certificateLink) {
        this.certificateLink = certificateLink;
    }

    public String getCertificateLink() {
        return certificateLink;
    }

    public void setWebinarTitle(String webinarTitle) {
        this.webinarTitle = webinarTitle;
    }

    public String getWebinarTitle() {
        return webinarTitle;
    }

    public void setWebinarType(String webinarType) {
        this.webinarType = webinarType;
    }

    public String getWebinarType() {
        return webinarType;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCredit() {
        return credit;
    }

    public void setWebinarStatus(String webinarStatus) {
        this.webinarStatus = webinarStatus;
    }

    public String getWebinarStatus() {
        return webinarStatus;
    }

    @Override
    public String toString() {
        return
                "MyCreditsItem{" +
                        "host_date = '" + hostDate + '\'' +
                        ",webinar_id = '" + webinarId + '\'' +
                        ",subject = '" + subject + '\'' +
                        ",joinUrl = '" + joinUrl + '\'' +
                        ",ce_credit = '" + ceCredit + '\'' +
                        ",webinar_credit_type = '" + WebinarCreditType + '\'' +
                        ",speaker_name = '" + speakerName + '\'' +
                        ",certificate_link = '" + certificateLink + '\'' +
                        ",webinar_title = '" + webinarTitle + '\'' +
                        ",webinar_type = '" + webinarType + '\'' +
                        ",credit = '" + credit + '\'' +
                        ",webinar_status = '" + webinarStatus + '\'' +
                        "}";
    }
}