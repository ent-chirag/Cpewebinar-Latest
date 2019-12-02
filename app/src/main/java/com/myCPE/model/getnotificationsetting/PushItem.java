package com.myCPE.model.getnotificationsetting;

import com.google.gson.annotations.SerializedName;


public class PushItem{

	@SerializedName("label_name")
	private String labelName;

	@SerializedName("status")
	private boolean status;

	public void setLabelName(String labelName){
		this.labelName = labelName;
	}

	public String getLabelName(){
		return labelName;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"PushItem{" + 
			"label_name = '" + labelName + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}