package me.sridharpatil.ecom.userservice.services.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmailRecipient {
    private String to;
    private String name;

    public EmailRecipient(String to, String name) {
        this.to = to;
        this.name = name;
    }
}
