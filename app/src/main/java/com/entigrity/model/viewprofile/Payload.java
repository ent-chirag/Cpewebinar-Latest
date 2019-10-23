package com.entigrity.model.viewprofile;


import com.google.gson.annotations.SerializedName;


public class Payload{

	@SerializedName("data")
	private Data data;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}