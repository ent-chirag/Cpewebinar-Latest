package com.entigrity.model.topicsofinterestn;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Payload{

	@SerializedName("topic_of_interests")
	private List<TopicOfInterestsItem> topicOfInterests;

	public void setTopicOfInterests(List<TopicOfInterestsItem> topicOfInterests){
		this.topicOfInterests = topicOfInterests;
	}

	public List<TopicOfInterestsItem> getTopicOfInterests(){
		return topicOfInterests;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"topic_of_interests = '" + topicOfInterests + '\'' + 
			"}";
		}
}