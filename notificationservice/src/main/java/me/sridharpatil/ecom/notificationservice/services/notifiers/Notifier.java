package me.sridharpatil.ecom.notificationservice.services.notifiers;

import jakarta.mail.MessagingException;
import me.sridharpatil.ecom.notificationservice.dtos.Recipient;
import me.sridharpatil.ecom.notificationservice.models.EventType;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface Notifier {
    void send(Recipient recipient, EventType eventType, Map<String, String> variables) throws MessagingException, UnsupportedEncodingException;
}
