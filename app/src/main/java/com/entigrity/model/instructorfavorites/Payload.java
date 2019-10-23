package com.entigrity.model.instructorfavorites;

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


    @SerializedName("my_favorite_speaker")
    private List<MyFavoriteSpeakerItem> myFavoriteSpeaker;

    public void setMyFavoriteSpeaker(List<MyFavoriteSpeakerItem> myFavoriteSpeaker) {
        this.myFavoriteSpeaker = myFavoriteSpeaker;
    }

    public List<MyFavoriteSpeakerItem> getMyFavoriteSpeaker() {
        return myFavoriteSpeaker;
    }

    @Override
    public String toString() {
        return
                "Payload{" +
                        "access_token = '" + accessToken + '\'' +
                        "my_favorite_speaker = '" + myFavoriteSpeaker + '\'' +
                        "}";
    }
}