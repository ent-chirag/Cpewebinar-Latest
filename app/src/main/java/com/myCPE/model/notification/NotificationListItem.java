package com.myCPE.model.notification;

import com.google.gson.annotations.SerializedName;


public class NotificationListItem {

    @SerializedName("is_read")
    private String isRead;

    public String getWebinartype() {
        return webinartype;
    }

    public void setWebinartype(String webinartype) {
        this.webinartype = webinartype;
    }

    @SerializedName("webinar_type")
    private String webinartype;


    @SerializedName("notification_type")
    private String notificationType;

    @SerializedName("image")
    private String image;

    @SerializedName("webinar_id")
    private int webinarId;

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    @SerializedName("notification_title")
    private String notificationTitle;

    @SerializedName("notification_message")
    private String notificationMessage;


    public int getWebinarflag() {
        return webinarflag;
    }

    public void setWebinarflag(int webinarflag) {
        this.webinarflag = webinarflag;
    }

    @SerializedName("timestamp")
    private int timestamp;

    @SerializedName("webinar_flag")
    private int webinarflag;


    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setWebinarId(int webinarId) {
        this.webinarId = webinarId;
    }

    public int getWebinarId() {
        return webinarId;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return
                "NotificationListItem{" +
                        "is_read = '" + isRead + '\'' +
                        ",notification_type = '" + notificationType + '\'' +
                        ",notification_message = '" + notificationMessage + '\'' +
                        ",webinar_type = '" + webinartype + '\'' +
                        ",webinar_flag = '" + webinarflag + '\'' +
                        ",image = '" + image + '\'' +
                        ",webinar_id = '" + webinarId + '\'' +
                        ",notification_title = '" + notificationTitle + '\'' +
                        ",timestamp = '" + timestamp + '\'' +
                        "}";
    }
}