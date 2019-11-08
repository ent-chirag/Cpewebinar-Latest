package com.entigrity.model.viewprofile;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Data {

    @SerializedName("country")
    private String country;

    @SerializedName("industry_id")
    private int industryId;

    @SerializedName("education")
    private List<EducationItem> education;

    @SerializedName("city")
    private String city;

    @SerializedName("jobtitle_name")
    private String jobtitleName;

    @SerializedName("user_type")
    private List<UserTypeItem> userType;

    @SerializedName("contact_no")
    private String contactNo;

    @SerializedName("phone")
    private String phone = "";

    @SerializedName("jobtitle_id")
    private int jobtitleId;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @SerializedName("user_type_id")
    private String userTypeId;

    @SerializedName("id")
    private int id;

    @SerializedName("state_id")
    private int stateId;

    @SerializedName("state")
    private String state;

    @SerializedName("credit")
    private int credit;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("email")
    private String email;

    @SerializedName("industry_name")
    private String industryName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("profile_picture")
    private String profilePicture;

    @SerializedName("education_ids")
    private String educationIds;

    @SerializedName("ptin_number")
    private String ptinNumber;

    @SerializedName("tags")
    private List<Object> tags;

    @SerializedName("zipcode")
    private String zipcode;

    @SerializedName("company_name")
    private String companyName;

    @SerializedName("firm_name")
    private String firmName;

    @SerializedName("designation")
    private String designation;

    @SerializedName("country_id")
    private int countryId;

    @SerializedName("city_id")
    private int cityId;

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setEducation(List<EducationItem> education) {
        this.education = education;
    }

    public List<EducationItem> getEducation() {
        return education;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setJobtitleName(String jobtitleName) {
        this.jobtitleName = jobtitleName;
    }

    public String getJobtitleName() {
        return jobtitleName;
    }

    public void setUserType(List<UserTypeItem> userType) {
        this.userType = userType;
    }

    public List<UserTypeItem> getUserType() {
        return userType;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setJobtitleId(int jobtitleId) {
        this.jobtitleId = jobtitleId;
    }

    public int getJobtitleId() {
        return jobtitleId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getCredit() {
        return credit;
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

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setEducationIds(String educationIds) {
        this.educationIds = educationIds;
    }

    public String getEducationIds() {
        return educationIds;
    }

    public void setPtinNumber(String ptinNumber) {
        this.ptinNumber = ptinNumber;
    }

    public String getPtinNumber() {
        return ptinNumber;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCityId() {
        return cityId;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "country = '" + country + '\'' +
                        ",industry_id = '" + industryId + '\'' +
                        ",education = '" + education + '\'' +
                        ",city = '" + city + '\'' +
                        ",jobtitle_name = '" + jobtitleName + '\'' +
                        ",user_type = '" + userType + '\'' +
                        ",contact_no = '" + contactNo + '\'' +
                        ",phone = '" + phone + '\'' +
                        ",jobtitle_id = '" + jobtitleId + '\'' +
                        ",user_type_id = '" + userTypeId + '\'' +
                        ",id = '" + id + '\'' +
                        ",state_id = '" + stateId + '\'' +
                        ",state = '" + state + '\'' +
                        ",credit = '" + credit + '\'' +
                        ",first_name = '" + firstName + '\'' +
                        ",email = '" + email + '\'' +
                        ",industry_name = '" + industryName + '\'' +
                        ",last_name = '" + lastName + '\'' +
                        ",profile_picture = '" + profilePicture + '\'' +
                        ",education_ids = '" + educationIds + '\'' +
                        ",ptin_number = '" + ptinNumber + '\'' +
                        ",tags = '" + tags + '\'' +
                        ",zipcode = '" + zipcode + '\'' +
                        ",company_name = '" + companyName + '\'' +
                        ",firm_name = '" + firmName + '\'' +
                        ",designation = '" + designation + '\'' +
                        ",country_id = '" + countryId + '\'' +
                        ",city_id = '" + cityId + '\'' +
                        "}";
    }
}