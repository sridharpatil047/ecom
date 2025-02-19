package me.sridharpatil.ecom.notificationservice.services.notifiers;

import jakarta.mail.MessagingException;
import me.sridharpatil.ecom.notificationservice.dtos.Recipient;
import me.sridharpatil.ecom.notificationservice.services.notifiers.templates.Message;

import java.io.UnsupportedEncodingException;

public interface Notifier {
    void send(Recipient recipient, Message message) throws MessagingException, UnsupportedEncodingException;
}
