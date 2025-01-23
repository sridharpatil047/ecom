package me.sridharpatil.ecom.notificationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Recipient {
    private String to;
    public Recipient(String to) {
        this.to = to;
    }
}
