package com.entigrity.model.webinar_details_new;

import com.google.gson.annotations.SerializedName;


public class NasbaApproved{

	@SerializedName("address")
	private String address;

	@SerializedName("nasba_profile_icon")
	private String nasbaProfileIcon;

	@SerializedName("nasba_profile_icon_qas")
	private String nasbaProfileIconQas;

	@SerializedName("nasba_desc")
	private String nasbaDesc;

	public String getNasbaProfileIconQas() {
		return nasbaProfileIconQas;
	}

	public void setNasbaProfileIconQas(String nasbaProfileIconQas) {
		this.nasbaProfileIconQas = nasbaProfileIconQas;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setNasbaProfileIcon(String nasbaProfileIcon){
		this.nasbaProfileIcon = nasbaProfileIcon;
	}

	public String getNasbaProfileIcon(){
		return nasbaProfileIcon;
	}

	public void setNasbaDesc(String nasbaDesc){
		this.nasbaDesc = nasbaDesc;
	}

	public String getNasbaDesc(){
		return nasbaDesc;
	}

	@Override
 	public String toString(){
		return 
			"NasbaApproved{" + 
			"address = '" + address + '\'' + 
			",nasba_profile_icon = '" + nasbaProfileIcon + '\'' + 
			",nasba_desc = '" + nasbaDesc + '\'' + 
			"}";
		}
}