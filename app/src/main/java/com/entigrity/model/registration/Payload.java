package com.entigrity.model.registration;

import com.google.gson.annotations.SerializedName;


public class Payload{

	@SerializedName("user")
	private User user;

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"user = '" + user + '\'' + 
			"}";
		}
}