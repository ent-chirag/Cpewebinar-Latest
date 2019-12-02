package com.myCPE.model.homewebinarnew;

import com.google.gson.annotations.SerializedName;

public class MyCertificateLinksItem {

	@SerializedName("certificate_link")
	private String certificateLink;

	@SerializedName("certificate_type")
	private String certificateType;

	public void setCertificateLink(String certificateLink){
		this.certificateLink = certificateLink;
	}

	public String getCertificateLink(){
		return certificateLink;
	}

	public void setCertificateType(String certificateType){
		this.certificateType = certificateType;
	}

	public String getCertificateType(){
		return certificateType;
	}

	@Override
 	public String toString(){
		return 
			"MyCertificateLinksItem{" + 
			"certificate_link = '" + certificateLink + '\'' + 
			",certificate_type = '" + certificateType + '\'' + 
			"}";
		}
}