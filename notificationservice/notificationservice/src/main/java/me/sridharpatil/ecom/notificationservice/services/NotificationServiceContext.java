package me.sridharpatil.ecom.notificationservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.notificationservice.dtos.Recipient;
import me.sridharpatil.ecom.notificationservice.properties.NotificationProperties;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Getter @Setter
@Component
public class NotificationServiceContext {

    NotificationProperties notificationProperties;
    NotificationService notificationService;

    public NotificationServiceContext(NotificationProperties notificationProperties,
                                      Map<String, NotificationService> notificationServices) {

        this.notificationProperties = notificationProperties;
        this.notificationService = notificationServices.get(notificationProperties.getSelector());

        if (this.notificationService == null) {
            throw new IllegalArgumentException("Invalid notification service selector");
        }
    }

    public void send(Recipient recipient, Event event) throws MessagingException, UnsupportedEncodingException, JsonProcessingException {
        notificationService.send(recipient, event);
    }
}
