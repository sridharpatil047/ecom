package me.sridharpatil.ecom.userservice.services.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SendEmailDto {
    private String to;
    private String subject;
    private String body;
}
