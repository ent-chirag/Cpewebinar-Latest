package com.entigrity.model.subject;

import com.google.gson.annotations.SerializedName;


public class SubjectItem {

    @SerializedName("subject")
    private String subject;

    @SerializedName("id")
    private int id;

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                "SubjectItem{" +
                        "subject = '" + subject + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}