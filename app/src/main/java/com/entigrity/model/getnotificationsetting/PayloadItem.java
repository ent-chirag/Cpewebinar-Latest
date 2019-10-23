package com.entigrity.model.getnotificationsetting;

import com.google.gson.annotations.SerializedName;

public class PayloadItem{

	@SerializedName("notification_settings")
	private NotificationSettings notificationSettings;

	public void setNotificationSettings(NotificationSettings notificationSettings){
		this.notificationSettings = notificationSettings;
	}

	public NotificationSettings getNotificationSettings(){
		return notificationSettings;
	}

	@Override
 	public String toString(){
		return 
			"PayloadItem{" + 
			"notification_settings = '" + notificationSettings + '\'' + 
			"}";
		}
}