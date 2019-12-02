package com.myCPE.model.webinar_like_dislike;

import com.google.gson.annotations.SerializedName;


public class Payload {

    @SerializedName("is_like")
    private String isLike;

    @SerializedName("total_count")
    private int totalcount;

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getIsLike() {
        return isLike;
    }

    @Override
    public String toString() {
        return
                "Payload{" +
                        "is_like = '" + isLike + '\'' +
                        "total_count = '" + totalcount + '\'' +
                        "}";
    }
}