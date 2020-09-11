package com.myCPE.model.webinar_list;

import com.google.gson.annotations.SerializedName;

public class RecentWebinarsItem{

	@SerializedName("webinar_image")
	private String webinarImage;

	@SerializedName("webinar_title")
	private String webinarTitle;

	@SerializedName("id")
	private String id;

	@SerializedName("webinar_type")
	private String webinarType;

	public String getWebinarImage(){
		return webinarImage;
	}

	public String getWebinarTitle(){
		return webinarTitle;
	}

	public String getId(){
		return id;
	}

	public String getWebinarType(){
		return webinarType;
	}
}