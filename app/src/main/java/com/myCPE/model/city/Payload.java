package com.myCPE.model.city;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Payload{

	@SerializedName("city")
	private List<CityItem> city;

	public void setCity(List<CityItem> city){
		this.city = city;
	}

	public List<CityItem> getCity(){
		return city;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"city = '" + city + '\'' + 
			"}";
		}
}