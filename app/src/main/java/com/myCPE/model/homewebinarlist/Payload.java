package com.myCPE.model.homewebinarlist;

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


    @SerializedName("webinar")
    private List<WebinarItem> webinar;

    public void setWebinar(List<WebinarItem> webinar) {
        this.webinar = webinar;
    }

    public List<WebinarItem> getWebinar() {
        return webinar;
    }

    @Override
    public String toString() {
        return
                "Payload{" +
                        "access_token = '" + accessToken + '\'' +
                        "webinar = '" + webinar + '\'' +
                        "}";
    }
}