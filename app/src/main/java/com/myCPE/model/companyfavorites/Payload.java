package com.myCPE.model.companyfavorites;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Payload {


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @SerializedName("access_token")

    private String accessToken;


    @SerializedName("my_favorite_company")
    private List<MyFavoriteCompanyItem> myFavoriteCompany;

    public void setMyFavoriteCompany(List<MyFavoriteCompanyItem> myFavoriteCompany) {
        this.myFavoriteCompany = myFavoriteCompany;
    }

    public List<MyFavoriteCompanyItem> getMyFavoriteCompany() {
        return myFavoriteCompany;
    }

    @Override
    public String toString() {
        return
                "Payload{" +
                        "access_token = '" + accessToken + '\'' +
                        "my_favorite_company = '" + myFavoriteCompany + '\'' +
                        "}";
    }
}