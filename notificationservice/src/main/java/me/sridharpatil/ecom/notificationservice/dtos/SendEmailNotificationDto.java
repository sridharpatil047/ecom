package me.sridharpatil.ecom.notificationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SendEmailNotificationDto extends SendNotificationDto {
    private String subject = "Notification from Ecom App";
    public SendEmailNotificationDto(String to, String message) {
        super(to, message);
    }
}
