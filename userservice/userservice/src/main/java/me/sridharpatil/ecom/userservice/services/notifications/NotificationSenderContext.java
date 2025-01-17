package me.sridharpatil.ecom.userservice.services.notifications;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.userservice.models.User;
import me.sridharpatil.ecom.userservice.properties.AppProps;
import me.sridharpatil.ecom.userservice.services.Event;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationSenderContext {

    AppProps appProps;
    NotificationSender notificationSender;

    public NotificationSenderContext(Map<String, NotificationSender> notificationSenders, AppProps appProps) {
        this.appProps = appProps;
        this.notificationSender = notificationSenders.get(this.appProps.getType());

        if (this.notificationSender == null) {
            throw new IllegalArgumentException("Invalid notification type");
        }
    }

    public void sendNotification(User user, Event event) throws JsonProcessingException {
        notificationSender.sendNotification(user, event);
    }
}
