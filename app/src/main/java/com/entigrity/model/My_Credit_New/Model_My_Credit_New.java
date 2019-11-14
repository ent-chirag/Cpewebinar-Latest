package com.entigrity.model.My_Credit_New;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Model_My_Credit_New {

	@SerializedName("payload")
	private List<PayloadItem> payload;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setPayload(List<PayloadItem> payload){
		this.payload = payload;
	}

	public List<PayloadItem> getPayload(){
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
			"Model_My_Credit_New{" +
			"payload = '" + payload + '\'' + 
			",success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}