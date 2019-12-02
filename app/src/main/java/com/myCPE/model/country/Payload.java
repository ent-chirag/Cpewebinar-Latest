package com.myCPE.model.country;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Payload {

    @SerializedName("country")
    private List<CountryItem> country;

    public void setCountry(List<CountryItem> country) {
        this.country = country;
    }

    public List<CountryItem> getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return
                "Payload{" +
                        "country = '" + country + '\'' +
                        "}";
    }
}