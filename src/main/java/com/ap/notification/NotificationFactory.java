package com.ap.notification;

import com.ap.constant.NotificationType;
import com.ap.model.User;

public class NotificationFactory {
    public static Notification createNotification(NotificationType type, User recipient, String message) {
        return switch (type) {
            case EMAIL -> new EmailNotification(recipient, message);
            case SMS -> new SMSNotification(recipient, message);
            case PUSH -> new PushNotification(recipient, message);
            // Add more notification types as needed
            default -> throw new IllegalArgumentException("Unknown notification type: " + type);
        };
    }
}
