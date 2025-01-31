package me.sridharpatil.ecom.notificationservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import me.sridharpatil.ecom.notificationservice.dtos.Recipient;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface NotificationService {
    void send(Recipient recipient, EventType eventType, Map<String, String> variables) throws JsonProcessingException, MessagingException, UnsupportedEncodingException;

    void handleOrderCancelledEvent(Long userId, Long orderId, String orderStatus) throws MessagingException, UnsupportedEncodingException, JsonProcessingException;

    void handleOrderPlacedEvent(Long userId, Long orderId, String orderStatus) throws MessagingException, UnsupportedEncodingException, JsonProcessingException;
}
