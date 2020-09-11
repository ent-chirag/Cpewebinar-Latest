package com.myCPE.model.webinar_list;

import com.google.gson.annotations.SerializedName;

public class WebinarItem{

	@SerializedName("tier_name")
	private String tierName;

	@SerializedName("join_url")
	private String joinUrl;

	@SerializedName("speaker_name")
	private String speakerName;

	@SerializedName("fee")
	private String fee;

	@SerializedName("is_card_save")
	private boolean isCardSave;

	@SerializedName("payment_link")
	private String paymentLink;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("webinar_title")
	private String webinarTitle;

	@SerializedName("redirection_url")
	private String redirectionUrl;

	@SerializedName("cpa_credit")
	private String cpaCredit;

	@SerializedName("id")
	private int id;

	@SerializedName("schedule_id")
	private int scheduleId;

	@SerializedName("webinar_type")
	private String webinarType;

	@SerializedName("status")
	private String status;

	@SerializedName("start_date")
	private String startDate;

	@SerializedName("start_time")
	private String startTime;

	@SerializedName("time_zone")
	private String timeZone;

	public String getTierName(){
		return tierName;
	}

	public String getJoinUrl(){
		return joinUrl;
	}

	public String getSpeakerName(){
		return speakerName;
	}

	public String getFee(){
		return fee;
	}

	public boolean isIsCardSave(){
		return isCardSave;
	}

	public String getPaymentLink(){
		return paymentLink;
	}

	public String getProductId(){
		return productId;
	}

	public String getWebinarTitle(){
		return webinarTitle;
	}

	public String getRedirectionUrl(){
		return redirectionUrl;
	}

	public String getCpaCredit(){
		return cpaCredit;
	}

	public int getId(){
		return id;
	}

	public String getWebinarType(){
		return webinarType;
	}

	public String getStatus(){
		return status;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
}