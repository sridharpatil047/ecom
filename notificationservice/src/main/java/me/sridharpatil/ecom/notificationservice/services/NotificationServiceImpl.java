package me.sridharpatil.ecom.notificationservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import me.sridharpatil.ecom.notificationservice.dtos.Recipient;
import me.sridharpatil.ecom.notificationservice.factories.RecipientFactory;
import me.sridharpatil.ecom.notificationservice.properties.NotificationProperties;
import me.sridharpatil.ecom.notificationservice.services.notifiers.Notifier;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService{

    Notifier notifier;
    NotificationProperties notificationProperties;

    public NotificationServiceImpl(Map<String, Notifier> notifiers, NotificationProperties notificationProperties) {
        this.notificationProperties = notificationProperties;
        this.notifier = notifiers.get(this.notificationProperties.getNotifier());
    }


    @Override
    public void send(Recipient recipient, EventType eventType, Map<String, String> variables) throws JsonProcessingException, MessagingException, UnsupportedEncodingException {
        notifier.send(recipient, eventType, variables);
    }

    @Override
    public void handleOrderCancelledEvent(Long userId, Long orderId, String orderStatus) throws MessagingException, UnsupportedEncodingException, JsonProcessingException {


        Recipient recipient = RecipientFactory.getRecipient(userId, notificationProperties.getNotifier());
        EventType eventType = EventType.ORDER_CANCELLED;
        Map<String, String> variables = Map.of("orderId", orderId.toString(), "orderStatus", orderStatus);

        this.send(recipient, eventType, variables);
    }

    @Override
    public void handleOrderPlacedEvent(Long userId, Long orderId, String orderStatus) throws MessagingException, UnsupportedEncodingException, JsonProcessingException {
        Recipient recipient = RecipientFactory.getRecipient(userId, notificationProperties.getNotifier());
        EventType eventType = EventType.ORDER_CONFIRMED;
        Map<String, String> variables = Map.of("orderId", orderId.toString(), "orderStatus", orderStatus);

        this.send(recipient, eventType, variables);
    }
}
