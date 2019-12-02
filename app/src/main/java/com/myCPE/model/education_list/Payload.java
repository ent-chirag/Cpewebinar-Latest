package com.myCPE.model.education_list;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Payload{

	@SerializedName("education_list")
	private List<EducationListItem> educationList;

	public void setEducationList(List<EducationListItem> educationList){
		this.educationList = educationList;
	}

	public List<EducationListItem> getEducationList(){
		return educationList;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"education_list = '" + educationList + '\'' + 
			"}";
		}
}