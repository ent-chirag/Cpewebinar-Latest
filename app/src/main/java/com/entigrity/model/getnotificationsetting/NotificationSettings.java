package com.entigrity.model.getnotificationsetting;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class NotificationSettings{

	@SerializedName("text")
	private List<TextItem> text;

	@SerializedName("push")
	private List<PushItem> push;

	public void setText(List<TextItem> text){
		this.text = text;
	}

	public List<TextItem> getText(){
		return text;
	}

	public void setPush(List<PushItem> push){
		this.push = push;
	}

	public List<PushItem> getPush(){
		return push;
	}

	@Override
 	public String toString(){
		return 
			"NotificationSettings{" + 
			"text = '" + text + '\'' + 
			",push = '" + push + '\'' + 
			"}";
		}
}