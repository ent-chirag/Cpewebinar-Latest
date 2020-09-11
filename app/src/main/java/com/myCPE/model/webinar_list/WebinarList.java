package com.myCPE.model.webinar_list;

import com.google.gson.annotations.SerializedName;

public class WebinarList{

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