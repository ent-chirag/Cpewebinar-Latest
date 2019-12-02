package com.myCPE.model.usertype;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Payload{

	@SerializedName("user_type")
	private List<UserTypeItem> userType;

	public void setUserType(List<UserTypeItem> userType){
		this.userType = userType;
	}

	public List<UserTypeItem> getUserType(){
		return userType;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"user_type = '" + userType + '\'' + 
			"}";
		}
}