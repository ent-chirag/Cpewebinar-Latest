package com.myCPE.model.company_details_new;

import com.google.gson.annotations.SerializedName;

public class Payload{

	@SerializedName("company")
	private Company company;

	public void setCompany(Company company){
		this.company = company;
	}

	public Company getCompany(){
		return company;
	}

	@Override
 	public String toString(){
		return 
			"Payload{" + 
			"company = '" + company + '\'' + 
			"}";
		}
}