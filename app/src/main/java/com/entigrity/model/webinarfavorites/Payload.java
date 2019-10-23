package com.entigrity.model.webinarfavorites;

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


    @SerializedName("my_favorite_webinar")
    private List<MyFavoriteWebinarItem> myFavoriteWebinar;

    public void setMyFavoriteWebinar(List<MyFavoriteWebinarItem> myFavoriteWebinar) {
        this.myFavoriteWebinar = myFavoriteWebinar;
    }

    public List<MyFavoriteWebinarItem> getMyFavoriteWebinar() {
        return myFavoriteWebinar;
    }

    @Override
    public String toString() {
        return
                "Payload{" +
                        "access_token = '" + accessToken + '\'' +
                        "my_favorite_webinar = '" + myFavoriteWebinar + '\'' +
                        "}";
    }
}