package com.ap.notification;

import com.ap.constant.NotificationType;
import com.ap.model.User;

public abstract class Notification {
    protected User recipient;
    protected String message;
    protected NotificationType type;

    public Notification(User recipient, String message, NotificationType type) {
        this.recipient = recipient;
        this.message = message;
        this.type = type;
    }

    public abstract void send();
}
