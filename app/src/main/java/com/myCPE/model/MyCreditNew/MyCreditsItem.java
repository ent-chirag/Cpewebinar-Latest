package com.myCPE.model.MyCreditNew;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MyCreditsItem{

	@SerializedName("subject")
	private String subject;

	@SerializedName("speaker_name")
	private String speakerName;

	@SerializedName("host_date")
	private String hostDate;

	@SerializedName("webinar_credit_type")
	private String webinarCreditType;

	@SerializedName("my_certificate_links")
	private List<MyCertificateLinksItem> myCertificateLinks;

	@SerializedName("ce_credit")
	private String ceCredit;

	@SerializedName("webinar_id")
	private int webinarId;

	@SerializedName("certificate_link")
	private List<String> certificateLink;

	@SerializedName("webinar_title")
	private String webinarTitle;

	@SerializedName("webinar_type")
	private String webinarType;

	@SerializedName("credit")
	private String credit;

	@SerializedName("joinUrl")
	private String joinUrl;

	@SerializedName("webinar_status")
	private String webinarStatus;

	public void setSubject(String subject){
		this.subject = subject;
	}

	public String getSubject(){
		return subject;
	}

	public void setSpeakerName(String speakerName){
		this.speakerName = speakerName;
	}

	public String getSpeakerName(){
		return speakerName;
	}

	public void setHostDate(String hostDate){
		this.hostDate = hostDate;
	}

	public String getHostDate(){
		return hostDate;
	}

	public void setWebinarCreditType(String webinarCreditType){
		this.webinarCreditType = webinarCreditType;
	}

	public String getWebinarCreditType(){
		return webinarCreditType;
	}

	public void setMyCertificateLinks(List<MyCertificateLinksItem> myCertificateLinks){
		this.myCertificateLinks = myCertificateLinks;
	}

	public List<MyCertificateLinksItem> getMyCertificateLinks(){
		return myCertificateLinks;
	}

	public void setCeCredit(String ceCredit){
		this.ceCredit = ceCredit;
	}

	public String getCeCredit(){
		return ceCredit;
	}

	public void setWebinarId(int webinarId){
		this.webinarId = webinarId;
	}

	public int getWebinarId(){
		return webinarId;
	}

	public void setCertificateLink(List<String> certificateLink){
		this.certificateLink = certificateLink;
	}

	public List<String> getCertificateLink(){
		return certificateLink;
	}

	public void setWebinarTitle(String webinarTitle){
		this.webinarTitle = webinarTitle;
	}

	public String getWebinarTitle(){
		return webinarTitle;
	}

	public void setWebinarType(String webinarType){
		this.webinarType = webinarType;
	}

	public String getWebinarType(){
		return webinarType;
	}

	public void setCredit(String credit){
		this.credit = credit;
	}

	public String getCredit(){
		return credit;
	}

	public void setJoinUrl(String joinUrl){
		this.joinUrl = joinUrl;
	}

	public String getJoinUrl(){
		return joinUrl;
	}

	public void setWebinarStatus(String webinarStatus){
		this.webinarStatus = webinarStatus;
	}

	public String getWebinarStatus(){
		return webinarStatus;
	}

	@Override
 	public String toString(){
		return 
			"MyCreditsItem{" + 
			"subject = '" + subject + '\'' + 
			",speaker_name = '" + speakerName + '\'' + 
			",host_date = '" + hostDate + '\'' + 
			",webinar_credit_type = '" + webinarCreditType + '\'' + 
			",my_certificate_links = '" + myCertificateLinks + '\'' + 
			",ce_credit = '" + ceCredit + '\'' + 
			",webinar_id = '" + webinarId + '\'' + 
			",certificate_link = '" + certificateLink + '\'' + 
			",webinar_title = '" + webinarTitle + '\'' + 
			",webinar_type = '" + webinarType + '\'' + 
			",credit = '" + credit + '\'' + 
			",joinUrl = '" + joinUrl + '\'' + 
			",webinar_status = '" + webinarStatus + '\'' + 
			"}";
		}
}