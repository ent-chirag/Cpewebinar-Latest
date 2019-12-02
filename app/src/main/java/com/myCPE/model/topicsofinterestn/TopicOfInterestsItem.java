package com.myCPE.model.topicsofinterestn;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class TopicOfInterestsItem{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("tags")
	private List<TagsItem> tags;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTags(List<TagsItem> tags){
		this.tags = tags;
	}

	public List<TagsItem> getTags(){
		return tags;
	}

	@Override
 	public String toString(){
		return 
			"TopicOfInterestsItem{" + 
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",tags = '" + tags + '\'' + 
			"}";
		}
}