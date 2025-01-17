package me.sridharpatil.ecom.notificationservice.consumers.dtos;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.notificationservice.dtos.EmailRecipient;
import me.sridharpatil.ecom.notificationservice.dtos.Recipient;
import me.sridharpatil.ecom.notificationservice.services.Event;

@Getter @Setter
public class SendEmailNotificationMessage {
    private EmailRecipient recipient;
    private Event event;
}
