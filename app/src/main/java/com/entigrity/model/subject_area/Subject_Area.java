package com.entigrity.model.subject_area;

import com.google.gson.annotations.SerializedName;


public class Subject_Area {

	@SerializedName("payload")
	private Payload payload;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setPayload(Payload payload){
		this.payload = payload;
	}

	public Payload getPayload(){
		return payload;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"Subject_Area{" +
			"payload = '" + payload + '\'' + 
			",success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}