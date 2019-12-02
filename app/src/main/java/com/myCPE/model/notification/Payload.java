package com.myCPE.model.notification;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Payload {

    @SerializedName("is_last")
    private boolean isLast;

    @SerializedName("notification_list")
    private List<NotificationListItem> notificationList;

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }

    public boolean isIsLast() {
        return isLast;
    }

    public void setNotificationList(List<NotificationListItem> notificationList) {
        this.notificationList = notificationList;
    }

    public List<NotificationListItem> getNotificationList() {
        return notificationList;
    }

    @Override
    public String toString() {
        return
                "Payload{" +
                        "is_last = '" + isLast + '\'' +
                        ",notification_list = '" + notificationList + '\'' +
                        "}";
    }
}