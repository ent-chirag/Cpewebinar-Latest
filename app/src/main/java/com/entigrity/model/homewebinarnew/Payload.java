package com.entigrity.model.homewebinarnew;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Payload {

    @SerializedName("is_last")
    private boolean isLast;


    @SerializedName("is_progress")
    private boolean isprogress;

    public boolean isIsprogress() {
        return isprogress;
    }

    public void setIsprogress(boolean isprogress) {
        this.isprogress = isprogress;
    }

    @SerializedName("webinar")
    private List<WebinarItem> webinar;

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }

    public boolean isIsLast() {
        return isLast;
    }

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
                        "is_last = '" + isLast + '\'' +
                        "is_progress = '" + isprogress + '\'' +
                        ",webinar = '" + webinar + '\'' +
                        "}";
    }
}