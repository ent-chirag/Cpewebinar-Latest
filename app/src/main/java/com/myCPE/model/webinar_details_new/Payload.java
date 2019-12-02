package com.myCPE.model.webinar_details_new;

import com.google.gson.annotations.SerializedName;


public class Payload {

    @SerializedName("webinar_detail")
    private WebinarDetail webinarDetail;

    public void setWebinarDetail(WebinarDetail webinarDetail) {
        this.webinarDetail = webinarDetail;
    }

    public WebinarDetail getWebinarDetail() {
        return webinarDetail;
    }

    @Override
    public String toString() {
        return
                "Payload{" +
                        "webinar_detail = '" + webinarDetail + '\'' +
                        "}";
    }
}