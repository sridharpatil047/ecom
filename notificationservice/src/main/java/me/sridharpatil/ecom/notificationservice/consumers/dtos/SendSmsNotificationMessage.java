package me.sridharpatil.ecom.notificationservice.consumers.dtos;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.notificationservice.dtos.SmsRecipient;
import me.sridharpatil.ecom.notificationservice.services.EventType;

@Getter @Setter
public class SendSmsNotificationMessage {
    private SmsRecipient recipient;
    private EventType eventType;
}
