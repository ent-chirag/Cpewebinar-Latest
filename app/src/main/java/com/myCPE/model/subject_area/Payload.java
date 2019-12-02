package com.myCPE.model.subject_area;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Payload{

	@SerializedName("topic_of_interests")
	private List<TopicOfInterestsItem> topicOfInterests;

	@SerializedName("subject_area")
	private List<SubjectAreaItem> subjectArea;

	public void setTopicOfInterests(List<TopicOfInterestsItem> topicOfInterests){
		this.topicOfInterests = topicOfInterests;
	}

	public List<TopicOfInterestsItem> getTopicOfInterests(){
		return topicOfInterests;
	}

	public void setSubjectArea(List<SubjectAreaItem> subjectArea){
		this.subjectArea = subjectArea;
	}

	public List<SubjectAreaItem> getSubjectArea(){
		return subjectArea;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"topic_of_interests = '" + topicOfInterests + '\'' + 
			",subject_area = '" + subjectArea + '\'' + 
			"}";
		}
}