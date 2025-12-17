package com.ap.notification;

import com.ap.constant.NotificationType;
import com.ap.model.User;

public class PushNotification extends Notification {
    public PushNotification(User recipient, String message) {
        super(recipient, message, NotificationType.PUSH);
    }

    @Override
    public void send() {
        // In a real implementation, this would send an actual push notification
        System.out.println("Sending push notification to user: " + recipient.getName());
        System.out.println("Title: Hotel Reservation Update");
        System.out.println("Message: " + message);
        System.out.println("---");
    }
}
