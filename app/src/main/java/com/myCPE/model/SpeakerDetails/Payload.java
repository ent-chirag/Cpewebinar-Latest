package com.myCPE.model.SpeakerDetails;

import com.google.gson.annotations.SerializedName;

public class Payload{

	@SerializedName("speaker")
	private Speaker speaker;

	public void setSpeaker(Speaker speaker){
		this.speaker = speaker;
	}

	public Speaker getSpeaker(){
		return speaker;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"speaker = '" + speaker + '\'' + 
			"}";
		}
}