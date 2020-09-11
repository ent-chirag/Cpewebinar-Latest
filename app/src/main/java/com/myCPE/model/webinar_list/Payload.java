package com.myCPE.model.webinar_list;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Payload{

	@SerializedName("is_last")
	private boolean isLast;

	@SerializedName("is_progress")
	private boolean isProgress;

	@SerializedName("RecentWebinars")
	private List<RecentWebinarsItem> recentWebinars;

	@SerializedName("webinar")
	private List<WebinarItem> webinar;

	public boolean isIsLast(){
		return isLast;
	}

	public boolean isIsProgress(){
		return isProgress;
	}

	public List<RecentWebinarsItem> getRecentWebinars(){
		return recentWebinars;
	}

	public List<WebinarItem> getWebinar(){
		return webinar;
	}
}