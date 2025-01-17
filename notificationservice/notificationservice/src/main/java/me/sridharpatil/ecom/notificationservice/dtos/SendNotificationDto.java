package me.sridharpatil.ecom.notificationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class SendNotificationDto {
    private String to;
    private String message;
    public SendNotificationDto(String to, String message) {
        this.to = to;
        this.message = message;
    }
}
