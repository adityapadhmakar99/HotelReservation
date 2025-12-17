package com.ap.notification;

import com.ap.constant.NotificationType;
import com.ap.model.User;

public class EmailNotification extends Notification {
    public EmailNotification(User recipient, String message) {
        super(recipient, message, NotificationType.EMAIL);
    }

    @Override
    public void send() {
        // In a real implementation, this would send an actual email
        System.out.println("Sending email to: " + recipient.getEmail());
        System.out.println("Subject: Hotel Reservation Update");
        System.out.println("Message: " + message);
        System.out.println("---");
    }
}
