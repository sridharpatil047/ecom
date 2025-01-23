package me.sridharpatil.ecom.userservice.services.dtos;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.userservice.services.Event;

@Getter @Setter
public class SendEmailNotificationMessage {
    private EmailRecipient recipient;
    private Event event;

    public SendEmailNotificationMessage(EmailRecipient recipient, Event event) {
        this.recipient = recipient;
        this.event = event;
    }
}
