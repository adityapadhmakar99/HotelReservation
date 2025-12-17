package com.ap.notification;

import com.ap.constant.NotificationType;
import com.ap.model.User;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private static NotificationService instance;
    private final List<Notification> notificationHistory;

    private NotificationService() {
        this.notificationHistory = new ArrayList<>();
    }

    public static synchronized NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    public void sendNotification(NotificationType type, User recipient, String message) {
        Notification notification = NotificationFactory.createNotification(type, recipient, message);
        notification.send();
        notificationHistory.add(notification);
    }

    public void sendAllNotificationTypes(User recipient, String message) {
        for (NotificationType type : NotificationType.values()) {
            sendNotification(type, recipient, message);
        }
    }

    public List<Notification> getNotificationHistory() {
        return new ArrayList<>(notificationHistory);
    }

    public void clearNotificationHistory() {
        notificationHistory.clear();
    }
}
