package me.sridharpatil.ecom.notificationservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import me.sridharpatil.ecom.notificationservice.dtos.Recipient;
import me.sridharpatil.ecom.notificationservice.services.notifiers.templates.Message;

import java.io.UnsupportedEncodingException;

public interface NotificationService {
    void send(Recipient recipient, Message message) throws JsonProcessingException, MessagingException, UnsupportedEncodingException;
    void handleOrderCancelledEvent(Long userId, Long orderId) throws MessagingException, UnsupportedEncodingException, JsonProcessingException;
    void handleOrderConfirmedEvent(Long userId, Long orderId) throws MessagingException, UnsupportedEncodingException, JsonProcessingException;
    void handleUserCreatedEvent(Long userId) throws MessagingException, UnsupportedEncodingException, JsonProcessingException;
    void handlePasswordResetEvent(Long userId, Integer otp) throws MessagingException, UnsupportedEncodingException, JsonProcessingException;
}
