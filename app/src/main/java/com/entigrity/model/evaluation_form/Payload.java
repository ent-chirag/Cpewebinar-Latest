package com.entigrity.model.evaluation_form;

import com.google.gson.annotations.SerializedName;


public class Payload{

	@SerializedName("link")
	private String link;

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"link = '" + link + '\'' + 
			"}";
		}
}