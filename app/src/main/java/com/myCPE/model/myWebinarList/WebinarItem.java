package com.myCPE.model.myWebinarList;

import com.google.gson.annotations.SerializedName;

public class WebinarItem{

	@SerializedName("tier_name")
	private String tierName;

	@SerializedName("image")
	private String image;

	@SerializedName("join_url")
	private String joinUrl;

	@SerializedName("vimeo_url")
	private String vimeoUrl;

	@SerializedName("speaker_name")
	private String speakerName;

	@SerializedName("fee")
	private String fee;

	@SerializedName("is_card_save")
	private boolean isCardSave;

	@SerializedName("time_zone")
	private String timeZone;

	@SerializedName("payment_link")
	private String paymentLink;

	@SerializedName("start_time")
	private String startTime;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("webinar_title")
	private String webinarTitle;

	@SerializedName("cpa_credit")
	private String cpaCredit;

	@SerializedName("id")
	private int id;

	@SerializedName("webinar_type")
	private String webinarType;

	@SerializedName("schedule_id")
	private int scheduleId;

	@SerializedName("start_date")
	private String startDate;

	@SerializedName("status")
	private String status;

	public String getTierName(){
		return tierName;
	}

	public String getImage(){
		return image;
	}

	public String getJoinUrl(){
		return joinUrl;
	}

	public String getVimeoUrl(){
		return vimeoUrl;
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

	public String getTimeZone(){
		return timeZone;
	}

	public String getPaymentLink(){
		return paymentLink;
	}

	public String getStartTime(){
		return startTime;
	}

	public String getProductId(){
		return productId;
	}

	public String getWebinarTitle(){
		return webinarTitle;
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

	public int getScheduleId(){
		return scheduleId;
	}

	public String getStartDate(){
		return startDate;
	}

	public String getStatus(){
		return status;
	}
}