package com.entigrity.model.topicsofinterestn;

import com.google.gson.annotations.SerializedName;


public class TagsItem{

	@SerializedName("name")
	private String name;

	@SerializedName("is_checked")
	private boolean isChecked;

	@SerializedName("id")
	private int id;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setIsChecked(boolean isChecked){
		this.isChecked = isChecked;
	}

	public boolean isIsChecked(){
		return isChecked;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"TagsItem{" + 
			"name = '" + name + '\'' + 
			",is_checked = '" + isChecked + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}