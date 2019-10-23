package com.entigrity.model.webinar_details_new;

import com.google.gson.annotations.SerializedName;


public class AboutPresententer {

    @SerializedName("desgnination")
    private String desgnination;


    @SerializedName("presenter_image")
    private String presenterImage;

    public String getPresenterImage() {
        return presenterImage;
    }

    public void setPresenterImage(String presenterImage) {
        this.presenterImage = presenterImage;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    @SerializedName("company_logo")
    private String companyLogo;


    @SerializedName("email_id")
    private String emailId;

    @SerializedName("qualification")
    private String qualification;

    @SerializedName("company_desc")
    private String companyDesc;

    @SerializedName("company_name")
    private String companyName;

    @SerializedName("company_website")
    private String companyWebsite;

    @SerializedName("name")
    private String name;

    @SerializedName("speaker_desc")
    private String speakerDesc;

    public void setDesgnination(String desgnination) {
        this.desgnination = desgnination;
    }

    public String getDesgnination() {
        return desgnination;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getQualification() {
        return qualification;
    }

    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }

    public String getCompanyDesc() {
        return companyDesc;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSpeakerDesc(String speakerDesc) {
        this.speakerDesc = speakerDesc;
    }

    public String getSpeakerDesc() {
        return speakerDesc;
    }

    @Override
    public String toString() {
        return
                "AboutPresententer{" +
                        "desgnination = '" + desgnination + '\'' +
                        ",email_id = '" + emailId + '\'' +
                        ",qualification = '" + qualification + '\'' +
                        ",company_desc = '" + companyDesc + '\'' +
                        ",company_name = '" + companyName + '\'' +
                        ",company_website = '" + companyWebsite + '\'' +
                        ",name = '" + name + '\'' +
                        ",presenter_image = '" + presenterImage + '\'' +
                        ",company_logo = '" + companyLogo + '\'' +
                        ",speaker_desc = '" + speakerDesc + '\'' +
                        "}";
    }
}