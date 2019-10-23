package com.entigrity.model.webinar_details_new;

import com.google.gson.annotations.SerializedName;


public class EaApproved{

	@SerializedName("ea_desc")
	private String eaDesc;

	@SerializedName("address")
	private String address;

	@SerializedName("ea_profile_icon")
	private String eaProfileIcon;

	public void setEaDesc(String eaDesc){
		this.eaDesc = eaDesc;
	}

	public String getEaDesc(){
		return eaDesc;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setEaProfileIcon(String eaProfileIcon){
		this.eaProfileIcon = eaProfileIcon;
	}

	public String getEaProfileIcon(){
		return eaProfileIcon;
	}

	@Override
 	public String toString(){
		return 
			"EaApproved{" + 
			"ea_desc = '" + eaDesc + '\'' + 
			",address = '" + address + '\'' + 
			",ea_profile_icon = '" + eaProfileIcon + '\'' + 
			"}";
		}
}