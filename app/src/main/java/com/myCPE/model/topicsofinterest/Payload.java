package com.myCPE.model.topicsofinterest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Payload{

	@SerializedName("tags")
	private List<TagsItem> tags;

	public void setTags(List<TagsItem> tags){
		this.tags = tags;
	}

	public List<TagsItem> getTags(){
		return tags;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"tags = '" + tags + '\'' + 
			"}";
		}
}