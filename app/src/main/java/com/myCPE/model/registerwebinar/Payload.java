package com.myCPE.model.registerwebinar;

import com.google.gson.annotations.SerializedName;

public class Payload{

	@SerializedName("join_url")
	private String joinUrl;

	@SerializedName("register_status")
	private String registerStatus;

	public void setJoinUrl(String joinUrl){
		this.joinUrl = joinUrl;
	}

	public String getJoinUrl(){
		return joinUrl;
	}

	public void setRegisterStatus(String registerStatus){
		this.registerStatus = registerStatus;
	}

	public String getRegisterStatus(){
		return registerStatus;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"join_url = '" + joinUrl + '\'' + 
			",register_status = '" + registerStatus + '\'' + 
			"}";
		}
}