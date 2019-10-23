package com.entigrity.model.getcontactusinfo;

import com.google.gson.annotations.SerializedName;

public class Payload{

	@SerializedName("email_id")
	private String emailId;

	@SerializedName("address")
	private String address;

	@SerializedName("contact_number")
	private String contactNumber;

	public void setEmailId(String emailId){
		this.emailId = emailId;
	}

	public String getEmailId(){
		return emailId;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}

	public String getContactNumber(){
		return contactNumber;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"email_id = '" + emailId + '\'' + 
			",address = '" + address + '\'' + 
			",contact_number = '" + contactNumber + '\'' + 
			"}";
		}
}