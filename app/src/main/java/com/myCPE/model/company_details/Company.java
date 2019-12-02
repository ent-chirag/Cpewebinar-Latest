package com.myCPE.model.company_details;

import com.google.gson.annotations.SerializedName;

public class Company{

	@SerializedName("number_of_webinar")
	private int numberOfWebinar;

	@SerializedName("country")
	private String country;

	@SerializedName("website")
	private String website;

	@SerializedName("city")
	private String city;

	@SerializedName("number_of_speaker")
	private int numberOfSpeaker;

	@SerializedName("name")
	private String name;

	@SerializedName("logo")
	private String logo;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("state")
	private String state;

	@SerializedName("favourite_unfavorite_status")
	private int favouriteUnfavoriteStatus;

	@SerializedName("contact_number")
	private String contactNumber;

	public void setNumberOfWebinar(int numberOfWebinar){
		this.numberOfWebinar = numberOfWebinar;
	}

	public int getNumberOfWebinar(){
		return numberOfWebinar;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setWebsite(String website){
		this.website = website;
	}

	public String getWebsite(){
		return website;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setNumberOfSpeaker(int numberOfSpeaker){
		this.numberOfSpeaker = numberOfSpeaker;
	}

	public int getNumberOfSpeaker(){
		return numberOfSpeaker;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return logo;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setFavouriteUnfavoriteStatus(int favouriteUnfavoriteStatus){
		this.favouriteUnfavoriteStatus = favouriteUnfavoriteStatus;
	}

	public int getFavouriteUnfavoriteStatus(){
		return favouriteUnfavoriteStatus;
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
			"Company{" + 
			"number_of_webinar = '" + numberOfWebinar + '\'' + 
			",country = '" + country + '\'' + 
			",website = '" + website + '\'' + 
			",city = '" + city + '\'' + 
			",number_of_speaker = '" + numberOfSpeaker + '\'' + 
			",name = '" + name + '\'' + 
			",logo = '" + logo + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",state = '" + state + '\'' + 
			",favourite_unfavorite_status = '" + favouriteUnfavoriteStatus + '\'' + 
			",contact_number = '" + contactNumber + '\'' + 
			"}";
		}
}