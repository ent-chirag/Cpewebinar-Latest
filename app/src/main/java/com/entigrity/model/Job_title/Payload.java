package com.entigrity.model.Job_title;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Payload {

    @SerializedName("job_title")
    private List<JobTitleItem> jobTitle;

    public void setJobTitle(List<JobTitleItem> jobTitle) {
        this.jobTitle = jobTitle;
    }

    public List<JobTitleItem> getJobTitle() {
        return jobTitle;
    }

    @Override
    public String toString() {
        return
                "Payload{" +
                        "job_title = '" + jobTitle + '\'' +
                        "}";
    }
}