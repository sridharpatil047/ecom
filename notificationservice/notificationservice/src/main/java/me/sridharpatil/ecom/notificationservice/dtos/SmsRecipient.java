package me.sridharpatil.ecom.notificationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SmsRecipient extends Recipient{
    public SmsRecipient(String to) {
        super(to);
    }
}
