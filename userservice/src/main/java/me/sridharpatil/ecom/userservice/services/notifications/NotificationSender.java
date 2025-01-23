package me.sridharpatil.ecom.userservice.services.notifications;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.services.Event;

public interface NotificationSender {
    void sendNotification(User user, Event event) throws JsonProcessingException;
}
