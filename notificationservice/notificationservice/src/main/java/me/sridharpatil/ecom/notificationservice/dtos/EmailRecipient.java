package me.sridharpatil.ecom.notificationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmailRecipient extends Recipient{
    private String name;
    public EmailRecipient(String to) {
        super(to);
    }
}
