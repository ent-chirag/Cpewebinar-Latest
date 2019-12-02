package com.myCPE.model.industry;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Payload {

    @SerializedName("industries_list")
    private List<IndustriesListItem> industriesList;

    public void setIndustriesList(List<IndustriesListItem> industriesList) {
        this.industriesList = industriesList;
    }

    public List<IndustriesListItem> getIndustriesList() {
        return industriesList;
    }

    @Override
    public String toString() {
        return
                "Payload{" +
                        "industries_list = '" + industriesList + '\'' +
                        "}";
    }
}