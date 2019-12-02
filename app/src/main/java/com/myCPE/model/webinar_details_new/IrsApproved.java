package com.myCPE.model.webinar_details_new;

import com.google.gson.annotations.SerializedName;


public class IrsApproved {

	@SerializedName("irs_address")
	private String irsAddress;

	@SerializedName("irs_profile_icon")
	private String irsProfileIcon;

	@SerializedName("irs_desc")
	private String irsDesc;

	public String getIrsAddress() {
		return irsAddress;
	}

	public void setIrsAddress(String irsAddress) {
		this.irsAddress = irsAddress;
	}

	public String getIrsProfileIcon() {
		return irsProfileIcon;
	}

	public void setIrsProfileIcon(String irsProfileIcon) {
		this.irsProfileIcon = irsProfileIcon;
	}

	public String getIrsDesc() {
		return irsDesc;
	}

	public void setIrsDesc(String irsDesc) {
		this.irsDesc = irsDesc;
	}

	@Override
	public String toString(){
		return
				"IrsApproved{" +
						"irs_address = '" + irsAddress + '\'' +
						",irs_profile_icon = '" + irsProfileIcon + '\'' +
						",irs_desc = '" + irsDesc + '\'' +
						"}";
	}
}