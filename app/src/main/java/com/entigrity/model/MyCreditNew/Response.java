package com.entigrity.model.MyCreditNew;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response{

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
			"Response{" + 
			"payload = '" + payload + '\'' + 
			",success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}