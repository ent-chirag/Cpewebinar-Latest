package com.myCPE.model.webinar_details;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Webinar {

    @SerializedName("finalResultStatus")
    private boolean finalResultStatus;

    @SerializedName("advance_preparation")
    private String advancePreparation;

    @SerializedName("documents")
    private String documents;

    @SerializedName("companyName")
    private String companyName;

    @SerializedName("fee")
    private String fee;

    @SerializedName("type")
    private String type;

    @SerializedName("reviewResultArray")
    private List<Object> reviewResultArray;

    @SerializedName("faq")
    private String faq;

    @SerializedName("instructional_method")
    private String instructionalMethod;

    @SerializedName("timeZoneArray")
    private TimeZoneArray timeZoneArray;

    @SerializedName("logo")
    private String logo;

    @SerializedName("upcoming_live_webinar")
    private UpcomingLiveWebinar upcomingLiveWebinar;

    @SerializedName("id")
    private int id;

    @SerializedName("webinar_type")
    private String webinarType;

    @SerializedName("webinarQusReviewResults")
    private List<Object> webinarQusReviewResults;

    @SerializedName("webinar_transcription")
    private String webinarTranscription;

    @SerializedName("finalPer")
    private String finalPer;

    @SerializedName("who_should_attend")
    private String whoShouldAttend;

    @SerializedName("pre_requirement")
    private String preRequirement;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("webinarRegisters")
    private String webinarRegisters;

    @SerializedName("webinarQusFinalResults")
    private List<Object> webinarQusFinalResults;

    @SerializedName("webinar_like")
    private String webinarLike;

    @SerializedName("status")
    private String status;

    @SerializedName("subject_area")
    private String subjectArea;

    @SerializedName("course_level")
    private String courseLevel;

    @SerializedName("displayFinalExam")
    private boolean displayFinalExam;

    @SerializedName("company_website")
    private String companyWebsite;

    @SerializedName("webinar_image")
    private String webinarImage;

    @SerializedName("description")
    private String description;

    @SerializedName("title")
    private String title;

    @SerializedName("webinar_share_link")
    private String webinarShareLink;

    @SerializedName("duration")
    private String duration;

    @SerializedName("finalExamResult")
    private boolean finalExamResult;

    @SerializedName("recorded_date")
    private String recordedDate;

    @SerializedName("webinar_thumbnail_image")
    private String webinarThumbnailImage;

    @SerializedName("finalResultArray")
    private List<Object> finalResultArray;

    @SerializedName("selfStudyVideoDuration")
    private String selfStudyVideoDuration;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("email")
    private String email;

    @SerializedName("learning_objectives")
    private String learningObjectives;

    @SerializedName("vimeo_video")
    private String vimeoVideo;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("time_zone")
    private String timeZone;

    @SerializedName("url")
    private String url;

    @SerializedName("reviewResultStatus")
    private boolean reviewResultStatus;

    @SerializedName("presentation_length")
    private String presentationLength;

    @SerializedName("webinarQuestions")
    private List<WebinarQuestionsItem> webinarQuestions;

    @SerializedName("resultStatus")
    private String resultStatus;

    @SerializedName("testimonials")
    private List<TestimonialsItem> testimonials;

    @SerializedName("about_speaker")
    private String aboutSpeaker;

    @SerializedName("series")
    private String series;

    @SerializedName("cpa_credit")
    private String cpaCredit;

    @SerializedName("vimeo_embaded")
    private String vimeoEmbaded;

    @SerializedName("liveWebinars")
    private List<LiveWebinarsItem> liveWebinars;

    public void setFinalResultStatus(boolean finalResultStatus) {
        this.finalResultStatus = finalResultStatus;
    }

    public boolean isFinalResultStatus() {
        return finalResultStatus;
    }

    public void setAdvancePreparation(String advancePreparation) {
        this.advancePreparation = advancePreparation;
    }

    public String getAdvancePreparation() {
        return advancePreparation;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getDocuments() {
        return documents;
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

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setReviewResultArray(List<Object> reviewResultArray) {
        this.reviewResultArray = reviewResultArray;
    }

    public List<Object> getReviewResultArray() {
        return reviewResultArray;
    }

    public void setFaq(String faq) {
        this.faq = faq;
    }

    public String getFaq() {
        return faq;
    }

    public void setInstructionalMethod(String instructionalMethod) {
        this.instructionalMethod = instructionalMethod;
    }

    public String getInstructionalMethod() {
        return instructionalMethod;
    }

    public void setTimeZoneArray(TimeZoneArray timeZoneArray) {
        this.timeZoneArray = timeZoneArray;
    }

    public TimeZoneArray getTimeZoneArray() {
        return timeZoneArray;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setUpcomingLiveWebinar(UpcomingLiveWebinar upcomingLiveWebinar) {
        this.upcomingLiveWebinar = upcomingLiveWebinar;
    }

    public UpcomingLiveWebinar getUpcomingLiveWebinar() {
        return upcomingLiveWebinar;
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

    public void setWebinarQusReviewResults(List<Object> webinarQusReviewResults) {
        this.webinarQusReviewResults = webinarQusReviewResults;
    }

    public List<Object> getWebinarQusReviewResults() {
        return webinarQusReviewResults;
    }

    public void setWebinarTranscription(String webinarTranscription) {
        this.webinarTranscription = webinarTranscription;
    }

    public String getWebinarTranscription() {
        return webinarTranscription;
    }

    public void setFinalPer(String finalPer) {
        this.finalPer = finalPer;
    }

    public String getFinalPer() {
        return finalPer;
    }

    public void setWhoShouldAttend(String whoShouldAttend) {
        this.whoShouldAttend = whoShouldAttend;
    }

    public String getWhoShouldAttend() {
        return whoShouldAttend;
    }

    public void setPreRequirement(String preRequirement) {
        this.preRequirement = preRequirement;
    }

    public String getPreRequirement() {
        return preRequirement;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setWebinarRegisters(String webinarRegisters) {
        this.webinarRegisters = webinarRegisters;
    }

    public String getWebinarRegisters() {
        return webinarRegisters;
    }

    public void setWebinarQusFinalResults(List<Object> webinarQusFinalResults) {
        this.webinarQusFinalResults = webinarQusFinalResults;
    }

    public List<Object> getWebinarQusFinalResults() {
        return webinarQusFinalResults;
    }

    public void setWebinarLike(String webinarLike) {
        this.webinarLike = webinarLike;
    }

    public String getWebinarLike() {
        return webinarLike;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

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

    public void setDisplayFinalExam(boolean displayFinalExam) {
        this.displayFinalExam = displayFinalExam;
    }

    public boolean isDisplayFinalExam() {
        return displayFinalExam;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setWebinarImage(String webinarImage) {
        this.webinarImage = webinarImage;
    }

    public String getWebinarImage() {
        return webinarImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
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

    public void setFinalExamResult(boolean finalExamResult) {
        this.finalExamResult = finalExamResult;
    }

    public boolean isFinalExamResult() {
        return finalExamResult;
    }

    public void setRecordedDate(String recordedDate) {
        this.recordedDate = recordedDate;
    }

    public String getRecordedDate() {
        return recordedDate;
    }

    public void setWebinarThumbnailImage(String webinarThumbnailImage) {
        this.webinarThumbnailImage = webinarThumbnailImage;
    }

    public String getWebinarThumbnailImage() {
        return webinarThumbnailImage;
    }

    public void setFinalResultArray(List<Object> finalResultArray) {
        this.finalResultArray = finalResultArray;
    }

    public List<Object> getFinalResultArray() {
        return finalResultArray;
    }

    public void setSelfStudyVideoDuration(String selfStudyVideoDuration) {
        this.selfStudyVideoDuration = selfStudyVideoDuration;
    }

    public String getSelfStudyVideoDuration() {
        return selfStudyVideoDuration;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setLearningObjectives(String learningObjectives) {
        this.learningObjectives = learningObjectives;
    }

    public String getLearningObjectives() {
        return learningObjectives;
    }

    public void setVimeoVideo(String vimeoVideo) {
        this.vimeoVideo = vimeoVideo;
    }

    public String getVimeoVideo() {
        return vimeoVideo;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setReviewResultStatus(boolean reviewResultStatus) {
        this.reviewResultStatus = reviewResultStatus;
    }

    public boolean isReviewResultStatus() {
        return reviewResultStatus;
    }

    public void setPresentationLength(String presentationLength) {
        this.presentationLength = presentationLength;
    }

    public String getPresentationLength() {
        return presentationLength;
    }

    public void setWebinarQuestions(List<WebinarQuestionsItem> webinarQuestions) {
        this.webinarQuestions = webinarQuestions;
    }

    public List<WebinarQuestionsItem> getWebinarQuestions() {
        return webinarQuestions;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setTestimonials(List<TestimonialsItem> testimonials) {
        this.testimonials = testimonials;
    }

    public List<TestimonialsItem> getTestimonials() {
        return testimonials;
    }

    public void setAboutSpeaker(String aboutSpeaker) {
        this.aboutSpeaker = aboutSpeaker;
    }

    public String getAboutSpeaker() {
        return aboutSpeaker;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSeries() {
        return series;
    }

    public void setCpaCredit(String cpaCredit) {
        this.cpaCredit = cpaCredit;
    }

    public String getCpaCredit() {
        return cpaCredit;
    }

    public void setVimeoEmbaded(String vimeoEmbaded) {
        this.vimeoEmbaded = vimeoEmbaded;
    }

    public String getVimeoEmbaded() {
        return vimeoEmbaded;
    }

    public void setLiveWebinars(List<LiveWebinarsItem> liveWebinars) {
        this.liveWebinars = liveWebinars;
    }

    public List<LiveWebinarsItem> getLiveWebinars() {
        return liveWebinars;
    }

    @Override
    public String toString() {
        return
                "Webinar{" +
                        "finalResultStatus = '" + finalResultStatus + '\'' +
                        ",advance_preparation = '" + advancePreparation + '\'' +
                        ",documents = '" + documents + '\'' +
                        ",companyName = '" + companyName + '\'' +
                        ",fee = '" + fee + '\'' +
                        ",type = '" + type + '\'' +
                        ",reviewResultArray = '" + reviewResultArray + '\'' +
                        ",faq = '" + faq + '\'' +
                        ",instructional_method = '" + instructionalMethod + '\'' +
                        ",timeZoneArray = '" + timeZoneArray + '\'' +
                        ",logo = '" + logo + '\'' +
                        ",upcoming_live_webinar = '" + upcomingLiveWebinar + '\'' +
                        ",id = '" + id + '\'' +
                        ",webinar_type = '" + webinarType + '\'' +
                        ",webinarQusReviewResults = '" + webinarQusReviewResults + '\'' +
                        ",webinar_transcription = '" + webinarTranscription + '\'' +
                        ",finalPer = '" + finalPer + '\'' +
                        ",who_should_attend = '" + whoShouldAttend + '\'' +
                        ",pre_requirement = '" + preRequirement + '\'' +
                        ",start_time = '" + startTime + '\'' +
                        ",webinarRegisters = '" + webinarRegisters + '\'' +
                        ",webinarQusFinalResults = '" + webinarQusFinalResults + '\'' +
                        ",webinar_like = '" + webinarLike + '\'' +
                        ",status = '" + status + '\'' +
                        ",subject_area = '" + subjectArea + '\'' +
                        ",course_level = '" + courseLevel + '\'' +
                        ",displayFinalExam = '" + displayFinalExam + '\'' +
                        ",company_website = '" + companyWebsite + '\'' +
                        ",webinar_image = '" + webinarImage + '\'' +
                        ",description = '" + description + '\'' +
                        ",title = '" + title + '\'' +
                        ",webinar_share_link = '" + webinarShareLink + '\'' +
                        ",duration = '" + duration + '\'' +
                        ",finalExamResult = '" + finalExamResult + '\'' +
                        ",recorded_date = '" + recordedDate + '\'' +
                        ",webinar_thumbnail_image = '" + webinarThumbnailImage + '\'' +
                        ",finalResultArray = '" + finalResultArray + '\'' +
                        ",selfStudyVideoDuration = '" + selfStudyVideoDuration + '\'' +
                        ",first_name = '" + firstName + '\'' +
                        ",email = '" + email + '\'' +
                        ",learning_objectives = '" + learningObjectives + '\'' +
                        ",vimeo_video = '" + vimeoVideo + '\'' +
                        ",end_time = '" + endTime + '\'' +
                        ",last_name = '" + lastName + '\'' +
                        ",avatar = '" + avatar + '\'' +
                        ",time_zone = '" + timeZone + '\'' +
                        ",url = '" + url + '\'' +
                        ",reviewResultStatus = '" + reviewResultStatus + '\'' +
                        ",presentation_length = '" + presentationLength + '\'' +
                        ",webinarQuestions = '" + webinarQuestions + '\'' +
                        ",resultStatus = '" + resultStatus + '\'' +
                        ",testimonials = '" + testimonials + '\'' +
                        ",about_speaker = '" + aboutSpeaker + '\'' +
                        ",series = '" + series + '\'' +
                        ",cpa_credit = '" + cpaCredit + '\'' +
                        ",vimeo_embaded = '" + vimeoEmbaded + '\'' +
                        ",liveWebinars = '" + liveWebinars + '\'' +
                        "}";
    }
}