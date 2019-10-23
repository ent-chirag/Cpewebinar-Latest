package com.entigrity.model.instructor;

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

    @SerializedName("speakers")
    private List<SpeakersItem> speakers;

    public void setSpeakers(List<SpeakersItem> speakers) {
        this.speakers = speakers;
    }

    public List<SpeakersItem> getSpeakers() {
        return speakers;
    }

    @Override
    public String toString() {
        return
                "Payload{" +
                        "access_token = '" + accessToken + '\'' +
                        "speakers = '" + speakers + '\'' +
                        "}";
    }
}