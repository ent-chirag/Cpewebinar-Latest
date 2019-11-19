package com.entigrity.model.webinar_details_new;

import com.google.gson.annotations.SerializedName;

public class CtecApproved {


    @SerializedName("address")
    private String address;

    @SerializedName("ctec_desc")
    private String ctecdesc;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCtecdesc() {
        return ctecdesc;
    }

    public void setCtecdesc(String ctecdesc) {
        this.ctecdesc = ctecdesc;
    }

    public String getCtecprofileicon() {
        return ctecprofileicon;
    }

    public void setCtecprofileicon(String ctecprofileicon) {
        this.ctecprofileicon = ctecprofileicon;
    }

    @SerializedName("ctec_profile_icon")
    private String ctecprofileicon;


    @Override
    public String toString() {
        return
                "EaApproved{" +
                        "ctec_desc = '" + ctecdesc + '\'' +
                        ",address = '" + address + '\'' +
                        ",ctec_profile_icon = '" + ctecprofileicon + '\'' +
                        "}";
    }


}
