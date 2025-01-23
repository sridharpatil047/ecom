package me.sridharpatil.ecom.userservice.services.notifications;

import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.services.Event;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationSender implements NotificationSender {
    @Override
    public void sendNotification(User user, Event event) {
        // Send SMS notification
    }
}
