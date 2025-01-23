package me.sridharpatil.ecom.notificationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SendSmsNotificationDto extends SendNotificationDto{
    public SendSmsNotificationDto(String to, String message) {
        super(to, message);
    }
}
