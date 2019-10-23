package com.entigrity.model.webinarfavorites;

import com.google.gson.annotations.SerializedName;


public class Webinar_Favorite {

    @SerializedName("payload")
    private Payload payload;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return
                "Webinar_Favorite{" +
                        "payload = '" + payload + '\'' +
                        ",success = '" + success + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}