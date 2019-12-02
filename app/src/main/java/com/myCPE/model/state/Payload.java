package com.myCPE.model.state;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Payload{

	@SerializedName("state")
	private List<StateItem> state;

	public void setState(List<StateItem> state){
		this.state = state;
	}

	public List<StateItem> getState(){
		return state;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"state = '" + state + '\'' + 
			"}";
		}
}