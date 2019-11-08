package com.entigrity.model.webinar_details_new;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class WebinarDetail {

    @SerializedName("subject_area")
    private String subjectArea;

    public String getPaymentlink() {
        return paymentlink;
    }

    public void setPaymentlink(String paymentlink) {
        this.paymentlink = paymentlink;
    }

    @SerializedName("payment_link")
    private String paymentlink;


    public String getKeyterms() {
        return keyterms;
    }

    public void setKeyterms(String keyterms) {
        this.keyterms = keyterms;
    }

    @SerializedName("key_terms")
    private String keyterms;


    @SerializedName("overview_of_topic")
    private String overviewoftopic;

    public String getWhyshouldattend() {
        return whyshouldattend;
    }

    public void setWhyshouldattend(String whyshouldattend) {
        this.whyshouldattend = whyshouldattend;
    }

    @SerializedName("why_should_attend")
    private String whyshouldattend;

    public String getOverviewoftopic() {
        return overviewoftopic;
    }

    public void setOverviewoftopic(String overviewoftopic) {
        this.overviewoftopic = overviewoftopic;
    }

    @SerializedName("course_level")
    private String courseLevel;

    @SerializedName("program_description")
    private String programDescription;

    @SerializedName("advance_preparation")
    private String advancePreparation;

    @SerializedName("shareable_link")
    private String shareableLink;

    @SerializedName("timespent")
    private int timespent;

    @SerializedName("video_status")
    private boolean videoStatus;

    @SerializedName("webinar_date")
    private String webinarDate;

    @SerializedName("duration")
    private int duration;

    @SerializedName("Learning_objective")
    private String learningObjective;

    @SerializedName("like_dislike_status")
    private String likeDislikeStatus;

    @SerializedName("webinar_id")
    private int webinarId;

    @SerializedName("instructional_method")
    private String instructionalMethod;

    @SerializedName("faq")
    private String faq;

    @SerializedName("webinar_testimonial")
    private List<WebinarTestimonialItem> webinarTestimonial;

    @SerializedName("webinar_title")
    private String webinarTitle;

    @SerializedName("webinar_type")
    private String webinarType;

    public String getCeCredit() {
        return ceCredit;
    }

    public void setCeCredit(String ceCredit) {
        this.ceCredit = ceCredit;
    }

    @SerializedName("credit")
    private String credit;

    @SerializedName("ce_credit")
    private String ceCredit;

    @SerializedName("about_presententer")
    private AboutPresententer aboutPresententer;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("course_id")
    private String courseId;

    @SerializedName("join_url")
    private String joinUrl;

    @SerializedName("ea_approved")
    private EaApproved eaApproved;

    @SerializedName("cost")
    private String cost;

    @SerializedName("prerequisite")
    private String prerequisite;

    @SerializedName("who_should_attend")
    private List<String> whoShouldAttend;

    @SerializedName("webinar_thumbnail")
    private String webinarThumbnail;

    @SerializedName("time_zone")
    private String timeZone;

    public int getStartutctime() {
        return startutctime;
    }

    public void setStartutctime(int startutctime) {
        this.startutctime = startutctime;
    }

    @SerializedName("start_utc_time")
    private int startutctime;


    @SerializedName("webinar_video_url")
    private String webinarVideoUrl;

    @SerializedName("presentation_handout")
    private List<String> presentationHandout;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("watched")
    private String watched;

    @SerializedName("timezones")
    private List<TimezonesItem> timezones;

    @SerializedName("static_timezones")
    private List<StaticTimezonesItem> statictimezones;

    @SerializedName("series")
    private String series;

    @SerializedName("certificate_link")
    private List<String> certificateLink;

    @SerializedName("play_time_duration")
    private int playTimeDuration;

    @SerializedName("schedule_id")
    private int scheduleId;

    @SerializedName("refund_and_cancelation_policy")
    private String refundAndCancelationPolicy;

    @SerializedName("nasba_approved")
    private NasbaApproved nasbaApproved;

    @SerializedName("webinar_status")
    private String webinarStatus;

    @SerializedName("status")
    private String status;

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setAdvancePreparation(String advancePreparation) {
        this.advancePreparation = advancePreparation;
    }

    public String getAdvancePreparation() {
        return advancePreparation;
    }

    public void setShareableLink(String shareableLink) {
        this.shareableLink = shareableLink;
    }

    public String getShareableLink() {
        return shareableLink;
    }

    public void setTimespent(int timespent) {
        this.timespent = timespent;
    }

    public int getTimespent() {
        return timespent;
    }

    public void setVideoStatus(boolean videoStatus) {
        this.videoStatus = videoStatus;
    }

    public boolean isVideoStatus() {
        return videoStatus;
    }

    public void setWebinarDate(String webinarDate) {
        this.webinarDate = webinarDate;
    }

    public String getWebinarDate() {
        return webinarDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setLearningObjective(String learningObjective) {
        this.learningObjective = learningObjective;
    }

    public String getLearningObjective() {
        return learningObjective;
    }

    public void setLikeDislikeStatus(String likeDislikeStatus) {
        this.likeDislikeStatus = likeDislikeStatus;
    }

    public String getLikeDislikeStatus() {
        return likeDislikeStatus;
    }

    public void setWebinarId(int webinarId) {
        this.webinarId = webinarId;
    }

    public int getWebinarId() {
        return webinarId;
    }

    public void setInstructionalMethod(String instructionalMethod) {
        this.instructionalMethod = instructionalMethod;
    }

    public String getInstructionalMethod() {
        return instructionalMethod;
    }

    public void setFaq(String faq) {
        this.faq = faq;
    }

    public String getFaq() {
        return faq;
    }

    public void setWebinarTestimonial(List<WebinarTestimonialItem> webinarTestimonial) {
        this.webinarTestimonial = webinarTestimonial;
    }

    public List<WebinarTestimonialItem> getWebinarTestimonial() {
        return webinarTestimonial;
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

    public void setAboutPresententer(AboutPresententer aboutPresententer) {
        this.aboutPresententer = aboutPresententer;
    }

    public AboutPresententer getAboutPresententer() {
        return aboutPresententer;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setJoinUrl(String joinUrl) {
        this.joinUrl = joinUrl;
    }

    public String getJoinUrl() {
        return joinUrl;
    }

    public void setEaApproved(EaApproved eaApproved) {
        this.eaApproved = eaApproved;
    }

    public EaApproved getEaApproved() {
        return eaApproved;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCost() {
        return cost;
    }

    public void setPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public void setWhoShouldAttend(List<String> whoShouldAttend) {
        this.whoShouldAttend = whoShouldAttend;
    }

    public List<String> getWhoShouldAttend() {
        return whoShouldAttend;
    }

    public void setWebinarThumbnail(String webinarThumbnail) {
        this.webinarThumbnail = webinarThumbnail;
    }

    public String getWebinarThumbnail() {
        return webinarThumbnail;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setWebinarVideoUrl(String webinarVideoUrl) {
        this.webinarVideoUrl = webinarVideoUrl;
    }

    public String getWebinarVideoUrl() {
        return webinarVideoUrl;
    }

    public void setPresentationHandout(List<String> presentationHandout) {
        this.presentationHandout = presentationHandout;
    }

    public List<String> getPresentationHandout() {
        return presentationHandout;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setWatched(String watched) {
        this.watched = watched;
    }

    public String getWatched() {
        return watched;
    }

    public void setTimezones(List<TimezonesItem> timezones) {
        this.timezones = timezones;
    }

    public List<TimezonesItem> getTimezones() {
        return timezones;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSeries() {
        return series;
    }

    public void setCertificateLink(List<String> certificateLink) {
        this.certificateLink = certificateLink;
    }

    public List<String> getCertificateLink() {
        return certificateLink;
    }

    public void setPlayTimeDuration(int playTimeDuration) {
        this.playTimeDuration = playTimeDuration;
    }

    public int getPlayTimeDuration() {
        return playTimeDuration;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setRefundAndCancelationPolicy(String refundAndCancelationPolicy) {
        this.refundAndCancelationPolicy = refundAndCancelationPolicy;
    }

    public String getRefundAndCancelationPolicy() {
        return refundAndCancelationPolicy;
    }

    public void setNasbaApproved(NasbaApproved nasbaApproved) {
        this.nasbaApproved = nasbaApproved;
    }

    public NasbaApproved getNasbaApproved() {
        return nasbaApproved;
    }

    public void setWebinarStatus(String webinarStatus) {
        this.webinarStatus = webinarStatus;
    }

    public String getWebinarStatus() {
        return webinarStatus;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public List<StaticTimezonesItem> getStatictimezones() {
        return statictimezones;
    }

    public void setStatictimezones(List<StaticTimezonesItem> statictimezones) {
        this.statictimezones = statictimezones;
    }

    @Override
    public String toString() {
        return
                "WebinarDetail{" +
                        "subject_area = '" + subjectArea + '\'' +
                        ",course_level = '" + courseLevel + '\'' +
                        ",program_description = '" + programDescription + '\'' +
                        ",advance_preparation = '" + advancePreparation + '\'' +
                        ",shareable_link = '" + shareableLink + '\'' +
                        ",timespent = '" + timespent + '\'' +
                        ",video_status = '" + videoStatus + '\'' +
                        ",webinar_date = '" + webinarDate + '\'' +
                        ",duration = '" + duration + '\'' +
                        ",learning_objective = '" + learningObjective + '\'' +
                        ",like_dislike_status = '" + likeDislikeStatus + '\'' +
                        ",webinar_id = '" + webinarId + '\'' +
                        ",instructional_method = '" + instructionalMethod + '\'' +
                        ",faq = '" + faq + '\'' +
                        ",webinar_testimonial = '" + webinarTestimonial + '\'' +
                        ",webinar_title = '" + webinarTitle + '\'' +
                        ",webinar_type = '" + webinarType + '\'' +
                        ",credit = '" + credit + '\'' +
                        ",ce_credit = '" + ceCredit + '\'' +
                        ",about_presententer = '" + aboutPresententer + '\'' +
                        ",start_date = '" + startDate + '\'' +
                        ",course_id = '" + courseId + '\'' +
                        ",join_url = '" + joinUrl + '\'' +
                        ",ea_approved = '" + eaApproved + '\'' +
                        ",cost = '" + cost + '\'' +
                        ",prerequisite = '" + prerequisite + '\'' +
                        ",who_should_attend = '" + whoShouldAttend + '\'' +
                        ",webinar_thumbnail = '" + webinarThumbnail + '\'' +
                        ",time_zone = '" + timeZone + '\'' +
                        ",start_utc_time = '" + startutctime + '\'' +
                        ",webinar_video_url = '" + webinarVideoUrl + '\'' +
                        ",presentation_handout = '" + presentationHandout + '\'' +
                        ",start_time = '" + startTime + '\'' +
                        ",watched = '" + watched + '\'' +
                        ",timezones = '" + timezones + '\'' +
                        ",static_timezones = '" + statictimezones + '\'' +
                        ",series = '" + series + '\'' +
                        ",certificate_link = '" + certificateLink + '\'' +
                        ",payment_link = '" + paymentlink + '\'' +
                        ",play_time_duration = '" + playTimeDuration + '\'' +
                        ",schedule_id = '" + scheduleId + '\'' +
                        ",refund_and_cancelation_policy = '" + refundAndCancelationPolicy + '\'' +
                        ",nasba_approved = '" + nasbaApproved + '\'' +
                        ",webinar_status = '" + webinarStatus + '\'' +
                        ",status = '" + status + '\'' +
                        ",key_terms = '" + keyterms + '\'' +
                        ",overview_of_topic = '" + overviewoftopic + '\'' +
                        ",why_should_attend = '" + whyshouldattend + '\'' +
                        "}";
    }
}