package com.entigrity.model.company;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Payload {

    @SerializedName("company")
    private List<CompanyItem> company;

    public void setCompany(List<CompanyItem> company) {
        this.company = company;
    }

    public List<CompanyItem> getCompany() {
        return company;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @SerializedName("access_token")

    private String accessToken;

    @Override
    public String toString() {
        return
                "Payload{" +
                        "access_token = '" + accessToken + '\'' +
                        "company = '" + company + '\'' +
                        "}";
    }
}