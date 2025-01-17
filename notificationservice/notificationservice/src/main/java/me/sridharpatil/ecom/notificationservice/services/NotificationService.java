package me.sridharpatil.ecom.notificationservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import me.sridharpatil.ecom.notificationservice.dtos.Recipient;

import java.io.UnsupportedEncodingException;

public interface NotificationService {
    void send(Recipient recipient, Event event) throws JsonProcessingException, MessagingException, UnsupportedEncodingException;
}
