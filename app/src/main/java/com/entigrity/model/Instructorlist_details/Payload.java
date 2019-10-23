package com.entigrity.model.Instructorlist_details;

import com.google.gson.annotations.SerializedName;


public class Payload {


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @SerializedName("access_token")

    private String accessToken;


    @SerializedName("speaker")
    private Speaker speaker;

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    @Override
    public String toString() {
        return
                "Payload{" +
                        "access_token = '" + accessToken + '\'' +
                        "speaker = '" + speaker + '\'' +
                        "}";
    }
}