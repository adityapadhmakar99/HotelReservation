package com.ap.notification;

import com.ap.constant.NotificationType;
import com.ap.model.User;

public class SMSNotification extends Notification {
    public SMSNotification(User recipient, String message) {
        super(recipient, message, NotificationType.SMS);
    }

    @Override
    public void send() {
        // In a real implementation, this would send an actual SMS
        System.out.println("Sending SMS to: " + recipient.getPhoneNumber());
        System.out.println("Message: " + message);
        System.out.println("---");
    }
}
