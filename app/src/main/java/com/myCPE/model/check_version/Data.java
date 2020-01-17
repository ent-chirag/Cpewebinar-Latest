package com.myCPE.model.check_version;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("is_force_update")
	private boolean isForceUpdate;

	@SerializedName("is_update")
	private boolean isUpdate;

	@SerializedName("current_version")
	private String currentVersion;

	public void setIsForceUpdate(boolean isForceUpdate){
		this.isForceUpdate = isForceUpdate;
	}

	public boolean isIsForceUpdate(){
		return isForceUpdate;
	}

	public void setIsUpdate(boolean isUpdate){
		this.isUpdate = isUpdate;
	}

	public boolean isIsUpdate(){
		return isUpdate;
	}

	public void setCurrentVersion(String currentVersion){
		this.currentVersion = currentVersion;
	}

	public String getCurrentVersion(){
		return currentVersion;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"is_force_update = '" + isForceUpdate + '\'' + 
			",is_update = '" + isUpdate + '\'' + 
			",current_version = '" + currentVersion + '\'' + 
			"}";
		}
}