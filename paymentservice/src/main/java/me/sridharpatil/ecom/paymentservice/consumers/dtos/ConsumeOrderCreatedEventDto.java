package me.sridharpatil.ecom.paymentservice.consumers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ConsumeOrderCreatedEventDto {
    Long orderId;
    double amount;
}
