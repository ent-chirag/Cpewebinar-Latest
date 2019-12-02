package com.myCPE.model.subject;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Payload {

    @SerializedName("subject")
    private List<SubjectItem> subject;

    public void setSubject(List<SubjectItem> subject) {
        this.subject = subject;
    }

    public List<SubjectItem> getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return
                "Payload{" +
                        "subject = '" + subject + '\'' +
                        "}";
    }
}