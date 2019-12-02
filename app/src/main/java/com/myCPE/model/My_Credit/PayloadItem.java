package com.myCPE.model.My_Credit;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PayloadItem{

	@SerializedName("all_count")
	private int allCount;

	@SerializedName("pending_count")
	private String pendingCount;

	@SerializedName("full_name")
	private String fullName;

	@SerializedName("is_last")
	private boolean isLast;

	@SerializedName("upcoming_count")
	private String upcomingCount;

	@SerializedName("total_count")
	private int totalCount;

	@SerializedName("my_credits")
	private List<MyCreditsItem> myCredits;

	@SerializedName("completed_count")
	private String completedCount;

	@SerializedName("email")
	private String email;

	public void setAllCount(int allCount){
		this.allCount = allCount;
	}

	public int getAllCount(){
		return allCount;
	}

	public void setPendingCount(String pendingCount){
		this.pendingCount = pendingCount;
	}

	public String getPendingCount(){
		return pendingCount;
	}

	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	public String getFullName(){
		return fullName;
	}

	public void setIsLast(boolean isLast){
		this.isLast = isLast;
	}

	public boolean isIsLast(){
		return isLast;
	}

	public void setUpcomingCount(String upcomingCount){
		this.upcomingCount = upcomingCount;
	}

	public String getUpcomingCount(){
		return upcomingCount;
	}

	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public void setMyCredits(List<MyCreditsItem> myCredits){
		this.myCredits = myCredits;
	}

	public List<MyCreditsItem> getMyCredits(){
		return myCredits;
	}

	public void setCompletedCount(String completedCount){
		this.completedCount = completedCount;
	}

	public String getCompletedCount(){
		return completedCount;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"PayloadItem{" + 
			"all_count = '" + allCount + '\'' + 
			",pending_count = '" + pendingCount + '\'' + 
			",full_name = '" + fullName + '\'' + 
			",is_last = '" + isLast + '\'' + 
			",upcoming_count = '" + upcomingCount + '\'' + 
			",total_count = '" + totalCount + '\'' + 
			",my_credits = '" + myCredits + '\'' + 
			",completed_count = '" + completedCount + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}