package me.sridharpatil.ecom.notificationservice.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@SuperBuilder
public class EmailRecipient extends Recipient{
    private String email;
}
