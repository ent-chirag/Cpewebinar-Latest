package com.entigrity.model.webinar_details;

import com.google.gson.annotations.SerializedName;


public class LiveWebinarsItem{

	@SerializedName("start_time")
	private String startTime;

	@SerializedName("id")
	private int id;

	@SerializedName("webinar_type")
	private String webinarType;

	@SerializedName("title")
	private String title;

	@SerializedName("time_zone")
	private String timeZone;

	public void setStartTime(String startTime){
		this.startTime = startTime;
	}

	public String getStartTime(){
		return startTime;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setWebinarType(String webinarType){
		this.webinarType = webinarType;
	}

	public String getWebinarType(){
		return webinarType;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setTimeZone(String timeZone){
		this.timeZone = timeZone;
	}

	public String getTimeZone(){
		return timeZone;
	}

	@Override
 	public String toString(){
		return 
			"LiveWebinarsItem{" + 
			"start_time = '" + startTime + '\'' + 
			",id = '" + id + '\'' + 
			",webinar_type = '" + webinarType + '\'' + 
			",title = '" + title + '\'' + 
			",time_zone = '" + timeZone + '\'' + 
			"}";
		}
}