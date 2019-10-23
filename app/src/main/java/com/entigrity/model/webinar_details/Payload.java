package com.entigrity.model.webinar_details;

import com.google.gson.annotations.SerializedName;


public class Payload {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("webinar")
    private Webinar webinar;

    public void setWebinar(Webinar webinar) {
        this.webinar = webinar;
    }

    public Webinar getWebinar() {
        return webinar;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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