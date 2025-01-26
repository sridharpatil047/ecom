package me.sridharpatil.ecom.orderservice.services.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateOrderMsgDto {
    long orderId;
    double amount;
}
