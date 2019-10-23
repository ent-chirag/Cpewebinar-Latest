package com.entigrity.model.topicsofinterest;

import com.google.gson.annotations.SerializedName;

public class TagsItem {

    @SerializedName("id")
    private int id;

    @SerializedName("tag")
    private String tag;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return
                "TagsItem{" +
                        "id = '" + id + '\'' +
                        ",tag = '" + tag + '\'' +
                        "}";
    }
}