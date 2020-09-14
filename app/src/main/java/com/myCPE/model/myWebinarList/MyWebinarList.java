package com.myCPE.model.myWebinarList;

import com.google.gson.annotations.SerializedName;

public class MyWebinarList{

	@SerializedName("payload")
	private Payload payload;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public Payload getPayload(){
		return payload;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}